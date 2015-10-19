//
//  CollectionLoginViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/1.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "CollectionLoginViewController.h"
#import "talkTableViewCell.h"

@interface CollectionLoginViewController ()<UITableViewDataSource, UITableViewDelegate,UIAlertViewDelegate>

@end

@implementation CollectionLoginViewController



- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.collectionLovingArray = [[NSMutableArray alloc]init];

    self.view.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:235 / 255.0 blue:236 / 255.0 alpha:1.0];
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 100, 30, 200, NAVIGATIONHEIGHT - 30)];
    titleL.text = @"我的心愿单";
    titleL.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleL.textAlignment = NSTextAlignmentCenter;
    titleL.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleL];
    [self createSubviews];
    
    [self getDetailData];
    
    [self.collectionTV addHeaderWithTarget:self action:@selector(headerRereshing)];
    [self.collectionTV headerBeginRefreshing];
    
    [self.collectionTV addFooterWithTarget:self action:@selector(footerRefreshing)];
}

-(void)createSubviews
{
    self.collectionTV = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT - 44) style:UITableViewStylePlain];
    self.collectionTV.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:235 / 255.0 blue:236 / 255.0 alpha:1.0];
    
    self.collectionTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.collectionTV];
    
    self.collectionTV.dataSource = self;
    self.collectionTV.delegate = self;
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.collectionLovingArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}


-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *collectionCellIdentifier = @"collectionCell";
    
    talkTableViewCell *colCell = [tableView dequeueReusableCellWithIdentifier:collectionCellIdentifier];
    
    if (colCell == nil) {
        
        colCell = [[talkTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:collectionCellIdentifier];
        colCell.selectionStyle = UITableViewCellSelectionStyleNone;
        
    }
    if (_collectionLovingArray.count == 0) {
        
        return colCell;
    }
    
    collectionModel *collectionM = [_collectionLovingArray objectAtIndex:indexPath.row];

    [colCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:[collectionM.iconUrl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    colCell.topicL.text = collectionM.title;
    colCell.tutorNameL.text = collectionM.name;
    
//    colCell.schoolL.text = collectionM.simpleShow1;
//    
//    colCell.positionL.text = collectionM.simpleShow2;
    colCell.simInfoForCollectionL.text = [NSString stringWithFormat:@"%@ %@", collectionM.simpleShow1, collectionM.simpleShow2];
    
    colCell.tutorPriceL.text = [NSString stringWithFormat:@"%@ 元/次", collectionM.price];
        
    return colCell;
}

-(void)getDetailData
{
    NSData *data1 = [AFNConnect createDataForCollectionLoving:[[[NSUserDefaults standardUserDefaults]objectForKey:@"userInfo"] objectForKey:@"uid"] page:[NSString stringWithFormat:@"%ld", _nextPage]];
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:data1 connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
        
            NSString *jsonStr = [dic objectForKey:@"data"];
            
            NSArray *jsonArray = (NSArray *)[jsonStr objectFromJSONString];
        
            if (jsonArray.count != 0) {
                for (NSMutableDictionary *dic in jsonArray) {
                    
                    collectionModel *collecionLoving = [[collectionModel alloc] init];
                    collecionLoving.iconUrl = [dic objectForKey:@"iconUrl"];
                    collecionLoving.name = [dic objectForKey:@"name"];
                    collecionLoving.price = [dic objectForKey:@"price"];
                    collecionLoving.simpleShow1 = [dic objectForKey:@"simpleShow1"];
                    collecionLoving.simpleShow2 = [dic objectForKey:@"simpleShow2"];
                    collecionLoving.time = [dic objectForKey:@"time"];
                    collecionLoving.title = [dic objectForKey:@"title"];
                    collecionLoving.teacherId = [[dic objectForKey:@"teacherId"] description];
                
                    [self.collectionLovingArray addObject:collecionLoving];
                }
            }
        } else {
        
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                if ([TagForClient shareTagDataHandle].isAlert) {
                    
                } else {
                    
                    [TagForClient shareTagDataHandle].isAlert = YES;
                    UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
                    [alertV show];
                }
            }
        }
        
        [self.collectionTV reloadData];
    }];
}

#pragma mark -- 下拉刷新 上拉加载
//下拉刷新
-(void)headerRereshing {
    
    self.isUpLoading = NO;//标记为下拉操作
    self.nextPage = 1;
    
    [self.collectionLovingArray removeAllObjects];
    [self getDetailData];//重新请求数据
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.collectionTV reloadData];
        
        [self.collectionTV headerEndRefreshing];
//    });
}

//上拉加载
-(void)footerRefreshing {
    
    self.isUpLoading = YES;
    self.nextPage ++;
    
    [self getDetailData];
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.collectionTV reloadData];
        
        [self.collectionTV footerEndRefreshing];
//    });
}

#pragma mark -- alertViewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
        
        if (buttonIndex == 1) {
            
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
            
            [self presentViewController:loginVC animated:YES completion:^{
                
            }];
        } else if (buttonIndex == 0) {
            
            [self.parentViewController.view sendSubviewToBack:self.view];
        }
    }
}

#pragma mark --进入导师详情页面
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_collectionLovingArray.count == 0) {
        return;
    }
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    collectionModel *collectionM = [self.collectionLovingArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = collectionM.teacherId;
    tutorVC.serviceTitleStr = collectionM.title;
    [self.navigationController pushViewController:tutorVC animated:YES];
    
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.collectionTV headerBeginRefreshing];
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        //[self getDetailData];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
