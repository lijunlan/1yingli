//
//  EmailViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EmailViewController.h"
#import "EmailModel.h"

@interface EmailViewController ()<UIAlertViewDelegate>

@property (nonatomic, assign) NSInteger nextPage;
@property (nonatomic, strong) NSMutableArray *notificationArray;

@end

@implementation EmailViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.nextPage = 1;
        self.notificationArray = [NSMutableArray array];
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(WIDTH / 2.0 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
    titleLabel.text = @"消息中心";
    titleLabel.font = [UIFont systemFontOfSize:35.0f];
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    self.myTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT - 40) style:UITableViewStylePlain];
    self.myTableView.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.myTableView.delegate = self;
    self.myTableView.dataSource = self;
    
    self.myTableView.showsVerticalScrollIndicator = NO;
    self.myTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    self.navigationController.navigationBar.hidden = YES;
    [self.view addSubview:self.myTableView];
    
//    取消cell的线
//    self.myTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
      self.navigationController.navigationBar.translucent = NO;
    
    //[self getNotification];
    
    [self.myTableView addHeaderWithTarget:self action:@selector(headerRefreshing)];
    [self.myTableView headerBeginRefreshing];
    
    [self.myTableView addFooterWithTarget:self action:@selector(footerRefreshing)];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_notificationArray.count == 0) {
        
        return 0;
    }
    
    return _notificationArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 70;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"myCell";
    EmailTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell = [[EmailTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
        cell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    }
    if (_notificationArray.count == 0) {
        
        return cell;
    }
    
    EmailModel *tmp = [_notificationArray objectAtIndex:indexPath.row];
    
    NSDate *tmpDate = [[NSDate alloc] initWithTimeIntervalSince1970:[tmp.time doubleValue] / 1000.0];
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateStyle:NSDateFormatterMediumStyle];
    [dateFormat setDateStyle:NSDateFormatterShortStyle];
    [dateFormat setDateFormat:@"yyyy-MM-dd\tHH:mm:ss"];
    
    cell.dateL.text = [dateFormat stringFromDate:tmpDate];
    cell.messageL.text = tmp.title;
    
    return cell;
}

//didSelect
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    EmailSecondPageViewController *emailSecondPageVC  = [[EmailSecondPageViewController alloc]init];
    
    if (_notificationArray.count == 0) {
        
    } else {
        emailSecondPageVC.emailDetailM = [_notificationArray objectAtIndex:indexPath.row];
    }
    
    [self.navigationController pushViewController:emailSecondPageVC animated:YES];
}

#pragma mark -- tableviewEdit


#pragma mark -- 获取数据
-(void)getNotification
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForNotification:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withPage:[NSString stringWithFormat:@"%ld", (long)self.nextPage]] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            NSMutableArray *jsonArray = [[dic objectForKey:@"data"] objectFromJSONString];
        
            for (NSMutableDictionary *tmpDic in jsonArray) {
                
                EmailModel *emailM = [[EmailModel alloc] init];
                
                emailM.notiId = [[tmpDic objectForKey:@"notiId"] description];
                emailM.title = [tmpDic objectForKey:@"title"];
                emailM.url = [tmpDic objectForKey:@"url"];
                emailM.time = [tmpDic objectForKey:@"time"];
                
                [self.notificationArray addObject:emailM];
            }
            
            [self.myTableView reloadData];
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
    }];
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
        if (buttonIndex == 1) {
            
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            
            [self presentViewController:loginVC animated:YES completion:^{
                
            }];
        } else if (buttonIndex == 0) {
        
            [self.parentViewController.view sendSubviewToBack:self.view];
        }
    }
}

#pragma mark -- 下拉刷新 上拉加载
//下拉刷新
-(void)headerRefreshing
{
    _nextPage = 1;
    
    [_notificationArray removeAllObjects];
    [self getNotification];
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [_myTableView reloadData];
        
        [_myTableView headerEndRefreshing];
//    });
}

//上拉加载
-(void)footerRefreshing
{
    _nextPage ++;
    
    [self getNotification];
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [_myTableView reloadData];
        
        [_myTableView footerEndRefreshing];
//    });
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        [self.myTableView headerBeginRefreshing];
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
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
