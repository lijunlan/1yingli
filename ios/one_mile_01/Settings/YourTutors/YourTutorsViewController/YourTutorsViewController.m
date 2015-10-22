//
//  YourTutorsViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "YourTutorsViewController.h"
#import "yourTutorsTableViewCell.h"
#import "ProductOrderModel.h"
#import "tutorHomeViewController.h"

@interface YourTutorsViewController ()<UIAlertViewDelegate>

@property (nonatomic, strong) NSMutableArray *youTutorArray;

@property (nonatomic, assign) NSInteger nextPage;
@property (nonatomic, assign) BOOL downUpdata;

@end

@implementation YourTutorsViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.youTutorArray = [NSMutableArray array];
        self.nextPage = 1;
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1];
    // Do any additional setup after loading the view.
    
    self.automaticallyAdjustsScrollViewInsets = NO;
    [self setSubviews];
    
    //[self getYourTutorData];
    
    [self.youTutorTV addHeaderWithTarget:self action:@selector(headerRefesh)];
    [self.youTutorTV headerBeginRefreshing];
    
    [self.youTutorTV addFooterWithTarget:self action:@selector(footerRefesh)];
}

-(void)setSubviews
{
    UIButton *pushBT = [UIButton buttonWithType:UIButtonTypeCustom];
    pushBT.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    //[pushBT setBackgroundColor:[UIColor blackColor]];
    [pushBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [pushBT addTarget:self action:@selector(popToSetting:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:pushBT];
    
    UILabel *youTutorTitleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 40, pushBT.frame.origin.y, 80, pushBT.frame.size.height)];
    youTutorTitleL.text = @"你的导师";
    youTutorTitleL.font = [UIFont systemFontOfSize:18.0f];
    [self.view addSubview:youTutorTitleL];
    
    self.youTutorTV = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT) style:UITableViewStylePlain];
    self.youTutorTV.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.youTutorTV.showsVerticalScrollIndicator = NO;
    self.youTutorTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [self.view addSubview:self.youTutorTV];
    
    self.youTutorTV.dataSource = self;
    self.youTutorTV.delegate = self;
}

#pragma mark -- buttonAction
-(void)popToSetting:(UIButton *)button
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _youTutorArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *yourTutorIdentifier = @"yourTutorCell";
    
    yourTutorsTableViewCell *yourTutorCell = [tableView dequeueReusableCellWithIdentifier:yourTutorIdentifier];
    
    if (yourTutorCell == nil) {
        
        yourTutorCell = [[yourTutorsTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:yourTutorIdentifier];
        yourTutorCell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    if (_youTutorArray.count == 0) {
        return yourTutorCell;
    }
    
    ProductOrderModel *orderTmp = [_youTutorArray objectAtIndex:indexPath.row];
    
    [yourTutorCell.youTutorIV sd_setImageWithURL:[NSURL URLWithString:orderTmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    yourTutorCell.youTutorNameL.text = orderTmp.teacherName;
    yourTutorCell.topicForyouTutorL.text = orderTmp.title;
    yourTutorCell.priceForyouTutorL.text = [NSString stringWithFormat:@"%@元/次",orderTmp.price];
    
    if ([[orderTmp.state substringToIndex:4] isEqualToString:@"0200"]) {
        
        yourTutorCell.stateForyouTutorL.text = @"订单已取消";
    } else if ([[orderTmp.state substringToIndex:4] isEqualToString:@"0300"]) {
        
        yourTutorCell.stateForyouTutorL.text = @"已付款，等待导师接受";
    } else if ([[orderTmp.state substringToIndex:4] isEqualToString:@"0400"]) {
        
        yourTutorCell.stateForyouTutorL.text = @"正在协商时间";
    } else if ([[orderTmp.state substringToIndex:4] isEqualToString:@"0500"]) {
    
        yourTutorCell.stateForyouTutorL.text = @"服务进行中";
    } else if ([[orderTmp.state substringToIndex:4] isEqualToString:@"0700"] && [[orderTmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0300"]) {
    
        yourTutorCell.stateForyouTutorL.text = @"订单已取消，正在处理订单";
    } else if ([[orderTmp.state substringToIndex:4] isEqualToString:@"1400"]) {
    
        yourTutorCell.stateForyouTutorL.text = @"订单已取消";
    } else if (([[orderTmp.state substringToIndex:4] isEqualToString:@"1500"] && [[orderTmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0400"]) || ([[orderTmp.state substringToIndex:4] isEqualToString:@"1500"] && [[orderTmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0500"])) {
    
        yourTutorCell.stateForyouTutorL.text = @"取消咨询，等待导师同意";
    } else if (([[orderTmp.state substringToIndex:4] isEqualToString:@"0700"] && [[orderTmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"]) || ([[orderTmp.state substringToIndex:4] isEqualToString:@"0700"] && [[orderTmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"])) {
    
        yourTutorCell.stateForyouTutorL.text = @"导师已同意退款，正在处理订单";
    } else if (([[orderTmp.state substringToIndex:4] isEqualToString:@"1300"] && [[orderTmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"]) || ([[orderTmp.state substringToIndex:4] isEqualToString:@"1300"] && [[orderTmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"])) {
    
        yourTutorCell.stateForyouTutorL.text = @"导师不同意退款，客服介入";
    } else if (([[orderTmp.state substringToIndex:4] isEqualToString:@"0700"] && [[orderTmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"]) || ([[orderTmp.state substringToIndex:4] isEqualToString:@"1300"] && [[orderTmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"])) {
    
        yourTutorCell.stateForyouTutorL.text = @"服务不满意，正在处理订单";
    } else if ([[orderTmp.state substringToIndex:4] isEqualToString:@"0900"] || [[orderTmp.state substringToIndex:4] isEqualToString:@"1000"] || [[orderTmp.state substringToIndex:4] isEqualToString:@"1010"]) {
    
        yourTutorCell.stateForyouTutorL.text = @"评价进行时。。。";
    }
    
    return yourTutorCell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_youTutorArray.count == 0) {
        return;
    }
    
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    ProductOrderModel *tmp = [_youTutorArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = tmp.teacherId;
    tutorVC.serviceTitleStr = tmp.title;
    [self.navigationController pushViewController:tutorVC animated:YES];
}

#pragma mark -- 获取数据
-(void)getYourTutorData
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderDetail:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withPage:[NSString stringWithFormat:@"%ld", (long)_nextPage]] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
           
            NSArray *jsonArray = [[dic objectForKey:@"data"] objectFromJSONString];
            
            for (NSMutableDictionary *tmpDic in jsonArray) {
                
                ProductOrderModel *orderM = [[ProductOrderModel alloc] init];
                
                orderM.contact = [tmpDic objectForKey:@"contact"];
                orderM.createTime = [tmpDic objectForKey:@"createTime"];
                orderM.email = [tmpDic objectForKey:@"email"];
                orderM.okTime = [tmpDic objectForKey:@"okTime"];
                orderM.name = [tmpDic objectForKey:@"name"];
                orderM.orderId = [tmpDic objectForKey:@"orderId"];
                orderM.phone = [tmpDic objectForKey:@"phone"];
                orderM.price = [tmpDic objectForKey:@"price"];
                orderM.question = [tmpDic objectForKey:@"question"];
                orderM.selectTimes = [tmpDic objectForKey:@"selectTimes"];
                orderM.state = [tmpDic objectForKey:@"state"];
                orderM.teacherId = [tmpDic objectForKey:@"teacherId"];
                orderM.teacherName = [tmpDic objectForKey:@"teacherName"];
                orderM.teacherUrl = [tmpDic objectForKey:@"teacherUrl"];
                orderM.time = [tmpDic objectForKey:@"time"];
                orderM.title = [tmpDic objectForKey:@"title"];
                orderM.userIntroduce = [tmpDic objectForKey:@"userIntroduce"];
                
                [self.youTutorArray addObject:orderM];
            }
        } else {
        
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alter = [[UIAlertView alloc]initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alter show];
            }
        }
        [self.youTutorTV reloadData];
    }];
}

#pragma mark -- alertviewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
        
        if (buttonIndex == 0) {
            
            [self.parentViewController.view sendSubviewToBack:self.view];
        } else {
            
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
            
            [self presentViewController:loginVC animated:YES completion:^{
                
            }];
        }
    }
}

#pragma mark -- 下拉刷新
-(void)headerRefesh {
    
    self.downUpdata = YES;
    self.nextPage = 1;
    //让菊花旋转起来
    //        self.MBHUD = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    //        self.MBHUD.labelText = @"正在加载中...";
    //        [self.MBHUD show:YES];
    
    [self getYourTutorData];
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    
        // 刷新表格
        [self.youTutorTV reloadData];
        [self.youTutorTV headerEndRefreshing];
//    });
    
}

#pragma mark -- 上拉加载
-(void)footerRefesh {
    
    self.nextPage ++;
    self.downUpdata = NO;
    //        self.MBHUD = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    //        self.MBHUD.labelText = @"正在加载中...";
    //        [self.MBHUD show:YES];
    
    [self getYourTutorData];
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    
        // 刷新表格
        [self.youTutorTV reloadData];
        [self.youTutorTV footerEndRefreshing];
//    });
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewDidDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
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
