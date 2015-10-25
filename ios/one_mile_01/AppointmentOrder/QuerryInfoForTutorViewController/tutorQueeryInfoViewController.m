//
//  tutorQueeryInfoViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorQueeryInfoViewController.h"
#import "DOPDropDownMenu.h"
#import "LoginViewController.h"

@interface tutorQueeryInfoViewController ()<DOPDropDownMenuDelegate,DOPDropDownMenuDataSource,UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate>

@property (nonatomic, strong) NSArray *tutorWaitingState;
@property (nonatomic, assign) NSInteger tutorTagIndex;
@property (nonatomic, strong) NSMutableArray *tutorWaitEnsureArray;
@property (nonatomic, strong) NSMutableArray *ensuredArray;
@property (nonatomic, strong) NSMutableArray *ensureTimeArray;
@property (nonatomic, strong) NSMutableArray *servingArray;
@property (nonatomic, strong) NSMutableArray *tutorCommentsArray;
@property (nonatomic, strong) NSMutableArray *tutorEndArray;

@end

@implementation tutorQueeryInfoViewController


-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.tutorWaitingState = @[ @"等待确认订单", @"导师已确认", @"导师已确定时间", @"服务进行中", @"双方已评价", @"服务结束"];
        self.TutorstateForOrder = TutorOrderStateForWaitEnsure;
        self.tutorWaitEnsureArray = [NSMutableArray array];
        self.ensuredArray = [NSMutableArray array];
        self.ensureTimeArray = [NSMutableArray array];
        self.servingArray = [NSMutableArray array];
        self.tutorCommentsArray = [NSMutableArray array];
        self.tutorEndArray = [NSMutableArray array];
        self.nextPage = 1;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];

    self.view.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:234 / 255.0 blue:235 / 255.0 alpha:1.0];
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
    //    titleL.backgroundColor = [UIColor yellowColor];
    titleL.text = @"订单详情";
    titleL.textAlignment = NSTextAlignmentCenter;
    titleL.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleL.textColor = [UIColor lightGrayColor];
    [self.view addSubview: titleL];
    
    UIButton *backBT = [UIButton buttonWithType:UIButtonTypeCustom];
    backBT.frame = CGRectMake(25, titleL.frame.origin.y + 8, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [backBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [self.view addSubview:backBT];
    
    [backBT addTarget:self action:@selector(TutorQuerryInfobackButton:) forControlEvents:UIControlEventTouchUpInside];
    [self createSubviews];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
//        [self getTutorOrderDetail];

    }
    
    [self.tutorQuerryInfoTableV addHeaderWithTarget:self action:@selector(headerRereshing)];
    [self.tutorQuerryInfoTableV headerBeginRefreshing];
    
    [self.tutorQuerryInfoTableV addFooterWithTarget:self action:@selector(TutorfooterRefreshing)];
}

-(void)createSubviews
{
    //添加下拉菜单
    DOPDropDownMenu *tutorWaitMenu = [[DOPDropDownMenu alloc] initWithOrigin:CGPointMake(8, NAVIGATIONHEIGHT + 5) andHeight:40];
    //waitMenu.backgroundColor = [UIColor yellowColor];
    tutorWaitMenu.layer.masksToBounds = YES;
    tutorWaitMenu.layer.cornerRadius = 20.0f;
    [self.view addSubview:tutorWaitMenu];
    
    tutorWaitMenu.dataSource = self;
    tutorWaitMenu.delegate = self;

    self.tutorQuerryInfoTableV = [[UITableView alloc] initWithFrame:CGRectMake(0, tutorWaitMenu.frame.size.height + NAVIGATIONHEIGHT + 10, WIDTH, HEIGHT - tutorWaitMenu.frame.size.height - 90) style:UITableViewStylePlain];
    self.tutorQuerryInfoTableV.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:234 / 255.0 blue:235 / 255.0 alpha:1.0];
    self.tutorQuerryInfoTableV.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tutorQuerryInfoTableV.showsVerticalScrollIndicator = NO;
    [self.view addSubview:self.tutorQuerryInfoTableV];
    
    self.tutorQuerryInfoTableV.dataSource = self;
    self.tutorQuerryInfoTableV.delegate = self;
}


#pragma mark -- 第三方方法
-(NSInteger)numberOfColumnsInMenu:(DOPDropDownMenu *)menu
{
    return 1;
}

-(NSInteger)menu:(DOPDropDownMenu *)menu numberOfRowsInColumn:(NSInteger)column
{
    return _tutorWaitingState.count;
}



-(NSString *)menu:(DOPDropDownMenu *)menu titleForRowAtIndexPath:(DOPIndexPath *)indexPath
{
    return self.tutorWaitingState[indexPath.row];
}

-(void)menu:(DOPDropDownMenu *)menu didSelectRowAtIndexPath:(DOPIndexPath *)indexPath
{
    self.tutorTagIndex = indexPath.row;

    switch (indexPath.row) {
            
        case 0:
            self.TutorstateForOrder = TutorOrderStateForWaitEnsure;
            [self.tutorQuerryInfoTableV headerBeginRefreshing];
            break;
            
        case 1:
            self.TutorstateForOrder = TutorOrderStateForEnsured;
            [self.tutorQuerryInfoTableV headerBeginRefreshing];
            break;
            
        case 2:
            self.TutorstateForOrder = TutorOrderStateForEnsureTime;
            [self.tutorQuerryInfoTableV headerBeginRefreshing];
            break;
            
        case 3:
            self.TutorstateForOrder = TutorOrderStateForServing;
            [self.tutorQuerryInfoTableV headerBeginRefreshing];
            break;
            
        case 4:
            self.TutorstateForOrder = TutorOrderStateForComments;
            [self.tutorQuerryInfoTableV headerBeginRefreshing];
            break;
            
        case 5:
            self.TutorstateForOrder = TutorOrderStateForEnd;
            [self.tutorQuerryInfoTableV headerBeginRefreshing];
            break;
            
        default:
            break;
            
    }
    [_tutorQuerryInfoTableV reloadData];
}

#pragma mark -- 请求数据
-(void)getOrderDetailForTutor:(NSInteger)state
{
    if (state == TutorOrderStateForWaitEnsure) {
        
        [self getOrderDetail:@"0300|0700,0300" withArray:_tutorWaitEnsureArray];
    } else if (state == TutorOrderStateForEnsured) {
    
        [self getOrderDetail:@"0400|1500,0400|0700,1500,0400|1300,1500,0400" withArray:_ensuredArray];
    } else if (state == TutorOrderStateForEnsureTime) {
    
        [self getOrderDetail:@"0500|1500,0500|0700,1500,0500|1300,1500,0500" withArray:_ensureTimeArray];
    } else if (state == TutorOrderStateForServing) {
    
        [self getOrderDetail:@"0500|0900,0500|0620|0700,0620|0900,1300,0620" withArray:_servingArray];
    } else if (state == TutorOrderStateForComments) {
    
        [self getOrderDetail:@"1000|1010" withArray:_tutorCommentsArray];
    } else {
    
        [self getOrderDetail:@"0800|1400" withArray:_tutorEndArray];
    }
}

-(void)getOrderDetail:(NSString *)state withArray:(NSMutableArray *)array
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTutorOrderDetail:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withTeacherID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"] withState:state withPage:[NSString stringWithFormat:@"%ld", _nextPage]] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            NSArray *dataArray = [[dic objectForKey:@"data"] objectFromJSONString];
            
            if (_nextPage == 1) {
                [self.tutorWaitEnsureArray removeAllObjects];
                [self.ensuredArray removeAllObjects];
                [self.ensureTimeArray removeAllObjects];
                [self.servingArray removeAllObjects];
                [self.tutorCommentsArray removeAllObjects];
                [self.tutorEndArray removeAllObjects];
            }
            
            for (NSMutableDictionary *tmpDic in dataArray) {
                
                tutorProductOrderModel *orderM = [[tutorProductOrderModel alloc] init];
                
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
                orderM.iconUrl = [tmpDic objectForKey:@"iconUrl"];
                orderM.time = [tmpDic objectForKey:@"time"];
                orderM.title = [tmpDic objectForKey:@"title"];
                orderM.userIntroduce = [tmpDic objectForKey:@"userIntroduce"];
                
                [array addObject:orderM];
            } 
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"error"]){
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                if ([TagForClient shareTagDataHandle].isAlert) {
                    
                } else {
                    [TagForClient shareTagDataHandle].isAlert = YES;
                    UIAlertView *alter = [[UIAlertView alloc]initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                    [alter show];
                }
            }
        }
        
        [self.tutorQuerryInfoTableV reloadData];
    }];
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
if (_TutorstateForOrder == TutorOrderStateForWaitEnsure) {
        
        return _tutorWaitEnsureArray.count;
    } else if (_TutorstateForOrder == TutorOrderStateForEnsured) {
        
        return _ensuredArray.count;
    } else if (_TutorstateForOrder == TutorOrderStateForEnsureTime) {
        
        return _ensureTimeArray.count;
    } else if (_TutorstateForOrder == TutorOrderStateForServing) {
        
        return _servingArray.count;
    } else if (_TutorstateForOrder == TutorOrderStateForComments) {
        
        return _tutorCommentsArray.count;
    } else {
        
        return _tutorEndArray.count;
    }
}


-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *querryInfoCellIdentifier = @"tutorQuerryCell";
    
    tutorQuerryInfoTableViewCell *tutorQuerryCell = [tableView dequeueReusableCellWithIdentifier:querryInfoCellIdentifier];
    
    if (tutorQuerryCell == nil) {
        
        tutorQuerryCell = [[tutorQuerryInfoTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:querryInfoCellIdentifier];
        tutorQuerryCell.selectionStyle = UITableViewCellSelectionStyleNone;
        tutorQuerryCell.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:234 / 255.0 blue:235 / 255.0 alpha:1.0];
    }
    
    if (_TutorstateForOrder == TutorOrderStateForWaitEnsure) {
        
        if (_tutorWaitEnsureArray.count == 0) {
            return tutorQuerryCell;
        }
        tutorProductOrderModel *tmp = [_tutorWaitEnsureArray objectAtIndex:indexPath.row];
        
        [tutorQuerryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        tutorQuerryCell.tutorNameL.text = tmp.name;
        tutorQuerryCell.topicL.text = tmp.title;
        tutorQuerryCell.moneyTeaL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0300"]) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"等待导师接受"];
        } else if([[tmp.state substringToIndex:4] isEqualToString:@"0700"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0300"]) {
        
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"等待退款"];
        }
    } else if (_TutorstateForOrder == TutorOrderStateForEnsured) {
        
        if (_ensuredArray.count == 0) {
            return tutorQuerryCell;
        }
        tutorProductOrderModel *tmp = [_ensuredArray objectAtIndex:indexPath.row];
        
        [tutorQuerryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        tutorQuerryCell.tutorNameL.text = tmp.name;
        tutorQuerryCell.topicL.text = tmp.title;
        tutorQuerryCell.moneyTeaL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0400"]) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"等待导师确认时间"];
        } else if([[tmp.state substringToIndex:4] isEqualToString:@"1500"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0400"]) {
        
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"学员已退单，等待确认"];
        } else if (([[tmp.state substringToIndex:4] isEqualToString:@"0700"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"]) ) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"已同意退款"];
        } else if (([[tmp.state substringToIndex:4] isEqualToString:@"1300"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"])) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"不同意退款，客服介入中"];
        }
    } else if (_TutorstateForOrder == TutorOrderStateForEnsureTime) {
        
        if (_ensureTimeArray.count == 0) {
            return tutorQuerryCell;
        }
        tutorProductOrderModel *tmp = [_ensureTimeArray objectAtIndex:indexPath.row];
        
        [tutorQuerryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        tutorQuerryCell.tutorNameL.text = tmp.name;
        tutorQuerryCell.topicL.text = tmp.title;
        tutorQuerryCell.moneyTeaL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0500"]) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"双方已确定好时间"];
        } else if ( ([[tmp.state substringToIndex:4] isEqualToString:@"0700"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) ){
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"已同意退款"];

        } else if ( ([[tmp.state substringToIndex:4] isEqualToString:@"1300"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) ){
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"不同意退款，客服介入中"];

        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1500"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0500"]){
        
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"学员已退单，等待确认"];
        }
    } else if (_TutorstateForOrder == TutorOrderStateForServing) {
        
        if (_servingArray.count == 0) {
            return tutorQuerryCell;
        }
        tutorProductOrderModel *tmp = [_servingArray objectAtIndex:indexPath.row];
        
        [tutorQuerryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        tutorQuerryCell.tutorNameL.text = tmp.name;
        tutorQuerryCell.topicL.text = tmp.title;
        tutorQuerryCell.moneyTeaL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4]isEqualToString:@"0500"]) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"正在服务中"];
        } else if ([[tmp.state substringToIndex:4]isEqualToString:@"0620"]) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"学员已退单，等待您的答复"];
        } else if ( ([[tmp.state substringToIndex:4] isEqualToString:@"1300"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"])){
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"不同意退款，等待客服介入"];
        } else if ( ([[tmp.state substringToIndex:4] isEqualToString:@"0700"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"]) ) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"学员不满意，同意退款"];
        } else if(([[tmp.state substringToIndex:4] isEqualToString:@"0900"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0500"])) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"服务完成，等待收款"];
        } else if (([[tmp.state substringToIndex:4] isEqualToString:@"1000"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"])) {
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"服务完成，等待评价"];
        }
    } else if (_TutorstateForOrder == TutorOrderStateForComments) {
        
        if (_tutorCommentsArray.count == 0) {
            return tutorQuerryCell;
        }
        tutorProductOrderModel *tmp = [_tutorCommentsArray objectAtIndex:indexPath.row];
        
        [tutorQuerryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        tutorQuerryCell.tutorNameL.text = tmp.name;
        tutorQuerryCell.topicL.text = tmp.title;
        tutorQuerryCell.moneyTeaL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"1010"]) {
        
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"评价完毕"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1000"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"1300"]){
            
            tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"不同意退款，对本次服务做出评价"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1000"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) {
            
           tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"服务完成，等待评价"];
        }
    } else {
        
        if (_tutorEndArray.count == 0) {
            return tutorQuerryCell;
        }
        tutorProductOrderModel *tmp = [_tutorEndArray objectAtIndex:indexPath.row];
        
        [tutorQuerryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        tutorQuerryCell.tutorNameL.text = tmp.name;
        tutorQuerryCell.topicL.text = tmp.title;
        tutorQuerryCell.moneyTeaL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        tutorQuerryCell.alertTeaStateL.text = [NSString stringWithFormat:@"服务结束"];
    }
    
    return tutorQuerryCell;
}

#pragma mark -- 自适应宽度
-(CGRect)autoFitWithWidth:(UILabel *)orglabel
{
    CGRect tmpRect = [orglabel.text boundingRectWithSize:CGSizeMake(200, MAXFLOAT) options:NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:orglabel.font} context:nil];
    tmpRect.size.height = orglabel.frame.size.height;
    return tmpRect;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{

    
    TutordetailQuerryInfoViewController *tutorDetailQuerryInfoVC = [[TutordetailQuerryInfoViewController alloc]init];
    
    tutorDetailQuerryInfoVC.tutorTagForColor = self.tutorTagIndex;
    
    if (self.TutorstateForOrder == TutorOrderStateForWaitEnsure ) {
        
        if (self.tutorCommentsArray == 0) {
            
        }else{
        tutorProductOrderModel *tmp = [self.tutorWaitEnsureArray objectAtIndex:indexPath.row];
            tutorDetailQuerryInfoVC.orderID = tmp.orderId;
            tutorDetailQuerryInfoVC.tutorDetailOrderS = tmp.state;
        }

    }else if(self.TutorstateForOrder == TutorOrderStateForEnsured){
        
        if (self.ensuredArray == 0) {
            
        }else{
        tutorProductOrderModel *tmp = [self.ensuredArray objectAtIndex:indexPath.row];
            tutorDetailQuerryInfoVC.orderID = tmp.orderId;
            tutorDetailQuerryInfoVC.tutorDetailOrderS = tmp.state;

        }
        
    }else if(self.TutorstateForOrder == TutorOrderStateForEnsureTime){
        if (self.ensureTimeArray == 0) {
            
        }else{
            
        tutorProductOrderModel *tmp = [self.ensureTimeArray objectAtIndex:indexPath.row];
            tutorDetailQuerryInfoVC.orderID = tmp.orderId;
            tutorDetailQuerryInfoVC.tutorDetailOrderS = tmp.state;

        }
        
    }else if (self.TutorstateForOrder == TutorOrderStateForServing){
        if (self.servingArray == 0) {
            
        }else{
        
        tutorProductOrderModel *tmp = [self.servingArray objectAtIndex:indexPath.row];
            tutorDetailQuerryInfoVC.orderID = tmp.orderId;
            tutorDetailQuerryInfoVC.tutorDetailOrderS = tmp.state;

        }
        
        
    }else if (self.TutorstateForOrder == TutorOrderStateForComments){
        if (self.tutorCommentsArray == 0) {
            
        }else{
        tutorProductOrderModel *tmp = [self.tutorCommentsArray objectAtIndex:indexPath.row];
            tutorDetailQuerryInfoVC.orderID = tmp.orderId;
            tutorDetailQuerryInfoVC.tutorDetailOrderS = tmp.state;
        }
    }else{
        if (self.tutorEndArray == 0) {
          
        }else{
        tutorProductOrderModel *tmp = [self.tutorEndArray objectAtIndex:indexPath.row];
            tutorDetailQuerryInfoVC.orderID = tmp.orderId;
            tutorDetailQuerryInfoVC.tutorDetailOrderS = tmp.state;
        }
    }
    
    [self.navigationController pushViewController:tutorDetailQuerryInfoVC animated:YES];

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

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if (self.navigationController.viewControllers.count == 1) {
        self.tabBarController.tabBar.hidden = NO;
        
        self.tutorQuerryInfoTableV.frame = CGRectMake(0, 40 + NAVIGATIONHEIGHT + 10, WIDTH, HEIGHT - 40 - 90);
    } else {
        
        self.tabBarController.tabBar.hidden = YES;
        self.tutorQuerryInfoTableV.frame = CGRectMake(0, NAVIGATIONHEIGHT + 10 + 40, WIDTH, HEIGHT - 40 - 90 + 49);
    }
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        [self.tutorQuerryInfoTableV headerBeginRefreshing];
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
    }
}


-(void)TutorQuerryInfobackButton:(UIButton *)button{
    
    [self dismissViewControllerAnimated:YES completion:^{
        
        
    }];
}

#pragma mark -- 下拉刷新 下拉加载更多
//下拉刷新
-(void)headerRereshing{
    
    self.isUpLoading = NO;//标记为下拉操作
    
    self.nextPage = 1;
    
    [self.tutorWaitEnsureArray removeAllObjects];
    [self.ensuredArray removeAllObjects];
    [self.ensureTimeArray removeAllObjects];
    [self.servingArray removeAllObjects];
    [self.tutorCommentsArray  removeAllObjects];
    [self.tutorEndArray removeAllObjects];
    
    [self getOrderDetailForTutor:_TutorstateForOrder];//重新请求数据
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.tutorQuerryInfoTableV reloadData];
        
        [self.tutorQuerryInfoTableV headerEndRefreshing];
    });
}

//上拉加载更多
- (void)TutorfooterRefreshing
{
    self.nextPage++;
    
    self.isUpLoading = YES;//标记为上拉操作
    
    [self getOrderDetailForTutor:_TutorstateForOrder];//请求数据
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.tutorQuerryInfoTableV reloadData];
        
        [self.tutorQuerryInfoTableV footerEndRefreshing];
    });
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
