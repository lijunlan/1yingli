//
//  QuerryInfoViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/26.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "QuerryInfoViewController.h"
#import "DOPDropDownMenu.h"
#import "QuerryInfoTableViewCell.h"
#import "ProductOrderModel.h"
#import "OrderForTutorModel.h"
#import "LoginViewController.h"

@interface QuerryInfoViewController ()<DOPDropDownMenuDataSource, DOPDropDownMenuDelegate, UIAlertViewDelegate, detailQuerryInfoInterface>

@property (nonatomic, strong) UIButton *backBT;

@property (nonatomic, strong) NSArray *waitingState;
@property (nonatomic, strong) NSMutableArray *noPayArray;
@property (nonatomic, strong) NSMutableArray *waitEnsureArray;
@property (nonatomic, strong) NSMutableArray *ensuredArray;
@property (nonatomic, strong) NSMutableArray *ensureTimeArray;
@property (nonatomic, strong) NSMutableArray *servingArray;
@property (nonatomic, strong) NSMutableArray *commentsArray;
@property (nonatomic, strong) NSMutableArray *endArray;

@property (nonatomic, assign) BOOL isUploading;
@property (nonatomic, assign) NSInteger nextPage;

@end

@implementation QuerryInfoViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.waitingState = @[@"未完成支付", @"等待导师确认", @"导师已确认", @"导师已确定时间", @"服务进行中", @"双方已评价", @"服务结束"];
        self.nextPage = 1;
        _stateForOrder = OrderStateForNoPay;
        self.noPayArray = [NSMutableArray array];
        self.waitEnsureArray = [NSMutableArray array];
        self.ensuredArray = [NSMutableArray array];
        self.ensureTimeArray = [NSMutableArray array];
        self.servingArray = [NSMutableArray array];
        self.commentsArray = [NSMutableArray array];
        self.endArray = [NSMutableArray array];
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:234 / 255.0 blue:235 / 255.0 alpha:1.0];
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
    //    titleL.backgroundColor = [UIColor yellowColor];
    titleL.text = @"订单详情";
    titleL.font = [UIFont systemFontOfSize:35.0f];
    titleL.textAlignment = NSTextAlignmentCenter;
    titleL.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleL.textColor = [UIColor lightGrayColor];
    [self.view addSubview: titleL];
    
    self.backBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.backBT.frame = CGRectMake(25, titleL.frame.origin.y + 8, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [self.backBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [self.view addSubview:self.backBT];
    
    [self.backBT addTarget:self action:@selector(backToBe:) forControlEvents:UIControlEventTouchUpInside];
    
    [self createSubviews];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
    }
    
    [self.querryInfoTV addHeaderWithTarget:self action:@selector(headerRefreshing)];
    [self.querryInfoTV headerBeginRefreshing];
    
    [self.querryInfoTV addFooterWithTarget:self action:@selector(footerRefreshing)];
}

-(void)backToBe:(UIButton *)button
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}


-(void)createSubviews
{
    //添加下拉菜单
    DOPDropDownMenu *waitMenu = [[DOPDropDownMenu alloc] initWithOrigin:CGPointMake(8, NAVIGATIONHEIGHT + 5) andHeight:40];
    //waitMenu.backgroundColor = [UIColor yellowColor];
    waitMenu.layer.masksToBounds = YES;
    waitMenu.layer.cornerRadius = 20.0f;
    [self.view addSubview:waitMenu];
    
    waitMenu.dataSource = self;
    waitMenu.delegate = self;
    
    self.querryInfoTV = [[UITableView alloc] initWithFrame:CGRectMake(0, waitMenu.frame.size.height + NAVIGATIONHEIGHT + 10, WIDTH, HEIGHT - waitMenu.frame.size.height - 90) style:UITableViewStylePlain];
    self.querryInfoTV.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:234 / 255.0 blue:235 / 255.0 alpha:1.0];
    self.querryInfoTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.querryInfoTV.showsVerticalScrollIndicator = NO;
    [self.view addSubview:_querryInfoTV];
    
    _querryInfoTV.dataSource = self;
    _querryInfoTV.delegate = self;
}

#pragma mark -- 第三方方法
-(NSInteger)numberOfColumnsInMenu:(DOPDropDownMenu *)menu
{
    return 1;
}

-(NSInteger)menu:(DOPDropDownMenu *)menu numberOfRowsInColumn:(NSInteger)column
{
    return _waitingState.count;
}

-(NSString *)menu:(DOPDropDownMenu *)menu titleForRowAtIndexPath:(DOPIndexPath *)indexPath
{
    return self.waitingState[indexPath.row];
}

-(void)menu:(DOPDropDownMenu *)menu didSelectRowAtIndexPath:(DOPIndexPath *)indexPath
{
    self.tagColor = indexPath.row;
    //NSLog(@"点击了 %ld - %ld 项目", indexPath.column, indexPath.row);
    
    switch (indexPath.row) {
        case 0:
            self.stateForOrder = OrderStateForNoPay;
            [self.querryInfoTV headerBeginRefreshing];
            //[self getOrderDetailForClient:OrderStateForNoPay];
            break;
            
        case 1:
            self.stateForOrder = OrderStateForWaitEnsure;
            [self.querryInfoTV headerBeginRefreshing];
            //[self getOrderDetailForClient:OrderStateForWaitEnsure];
            break;
            
        case 2:
            self.stateForOrder = OrderStateForEnsured;
            [self.querryInfoTV headerBeginRefreshing];
            //[self getOrderDetailForClient:OrderStateForEnsured];
            break;
            
        case 3:
            self.stateForOrder = OrderStateForEnsureTime;
            [self.querryInfoTV headerBeginRefreshing];
            //[self getOrderDetailForClient:OrderStateForEnsureTime];
            break;
            
        case 4:
            self.stateForOrder = OrderStateForServing;
            [self.querryInfoTV headerBeginRefreshing];
            //[self getOrderDetailForClient:OrderStateForServing];
            break;
            
        case 5:
            self.stateForOrder = OrderStateForComments;
            [self.querryInfoTV headerBeginRefreshing];
            //[self getOrderDetailForClient:OrderStateForComments];
            break;
            
        case 6:
            self.stateForOrder = OrderStateForEnd;
            [self.querryInfoTV headerBeginRefreshing];
            //[self getOrderDetailForClient:OrderStateForEnd];
            break;
            
        default:
            break;
    }
    
    [self.querryInfoTV reloadData];
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_stateForOrder == OrderStateForNoPay) {
        
        return _noPayArray.count;
    } else if (_stateForOrder == OrderStateForWaitEnsure) {
    
        return _waitEnsureArray.count;
    } else if (_stateForOrder == OrderStateForEnsured) {
    
        return _ensuredArray.count;
    } else if (_stateForOrder == OrderStateForEnsureTime) {
    
        return _ensureTimeArray.count;
    } else if (_stateForOrder == OrderStateForServing) {
    
        return _servingArray.count;
    } else if (_stateForOrder == OrderStateForComments) {
    
        return _commentsArray.count;
    } else {
    
        return _endArray.count;
    }
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *querryInfoCellIdentifier = @"querryCell";
    
    QuerryInfoTableViewCell *querryCell = [tableView dequeueReusableCellWithIdentifier:querryInfoCellIdentifier];
    
    if (querryCell == nil) {
        
        querryCell = [[QuerryInfoTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:querryInfoCellIdentifier];
        querryCell.selectionStyle = UITableViewCellSelectionStyleNone;
        querryCell.backgroundColor = [UIColor colorWithRed:232 / 255.0 green:234 / 255.0 blue:235 / 255.0 alpha:1.0];
    }
    
    if (_stateForOrder == OrderStateForNoPay) {
        
        if (_noPayArray.count == 0) {
            return querryCell;
        }
        ProductOrderModel *tmp = [_noPayArray objectAtIndex:indexPath.row];
        
        [querryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        querryCell.tutorNameL.text = tmp.teacherName;
        querryCell.topicL.text = tmp.title;
        querryCell.moneyL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0100"]) {
            
            querryCell.alertStateL.text = [NSString stringWithFormat:@"您已下单，请及时付款"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0200"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"订单已取消"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0700"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"正在处理订单"];
        } else {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"交易终止"];
        }
//        CGRect alertRect = [self fitWithLabelForQuerryinfo:querryCell.alertStateL];
//        querryCell.alertStateL.frame = CGRectMake(querryCell.alertStateL.frame.origin.x, querryCell.alertStateL.frame.origin.y, alertRect.size.width, alertRect.size.height);
        
    } else if (_stateForOrder == OrderStateForWaitEnsure) {
    
        if (_waitEnsureArray.count == 0) {
            return querryCell;
        }
        ProductOrderModel *tmp = [_waitEnsureArray objectAtIndex:indexPath.row];
        
        [querryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        querryCell.tutorNameL.text = tmp.teacherName;
        querryCell.topicL.text = tmp.title;
        querryCell.moneyL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0300"]) {
            
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师会在24小时内联系您"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0700"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"已取消咨询，订单正在处理中"];
        }
//        CGRect stateRect1 = [self fitWithLabelForQuerryinfo:querryCell.alertStateL];
//        querryCell.alertStateL.frame = CGRectMake(querryCell.alertStateL.frame.origin.x, querryCell.alertStateL.frame.origin.y, stateRect1.size.width, stateRect1.size.height);
    } else if (_stateForOrder == OrderStateForEnsured) {
    
        if (_ensuredArray.count == 0) {
            return querryCell;
        }
        ProductOrderModel *tmp = [_ensuredArray objectAtIndex:indexPath.row];
        
        [querryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        querryCell.tutorNameL.text = tmp.teacherName;
        querryCell.topicL.text = tmp.title;
        querryCell.moneyL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0400"]) {
            
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师已接受订单"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1500"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0400"]){
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"等待导师同意取消订单"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0700"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师同意退款，处理订单中"];
        } else {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师不同意退款，客服介入"];
        }
//        CGRect stateRect3 = [self fitWithLabelForQuerryinfo:querryCell.alertStateL];
//        querryCell.alertStateL.frame = CGRectMake(querryCell.moneyL.frame.origin.x + querryCell.moneyL.frame.size.width, querryCell.alertStateL.frame.origin.y, stateRect3.size.width, stateRect3.size.height);
    } else if (_stateForOrder == OrderStateForEnsureTime) {
    
        if (_ensureTimeArray.count == 0) {
            return querryCell;
        }
        ProductOrderModel *tmp = [_ensureTimeArray objectAtIndex:indexPath.row];
        
        [querryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        querryCell.tutorNameL.text = tmp.teacherName;
        querryCell.topicL.text = tmp.title;
        querryCell.moneyL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0500"]) {
            
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师已确定时间"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1500"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0500"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"已申请退款，等到导师回应"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1300"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师不同意退款，客服介入"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0700"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师同意退款，订单处理中"];
        }
//        CGRect stateRect3 = [self fitWithLabelForQuerryinfo:querryCell.alertStateL];
//        querryCell.alertStateL.frame = CGRectMake(querryCell.moneyL.frame.origin.x + querryCell.moneyL.frame.size.width, querryCell.alertStateL.frame.origin.y, stateRect3.size.width, stateRect3.size.height);
    } else if (_stateForOrder == OrderStateForServing) {
    
        if (_servingArray.count == 0) {
            return querryCell;
        }
        ProductOrderModel *tmp = [_servingArray objectAtIndex:indexPath.row];
        
        [querryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        querryCell.tutorNameL.text = tmp.teacherName;
        querryCell.topicL.text = tmp.title;
        querryCell.moneyL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"0900"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0500"]) {
            
            querryCell.alertStateL.text = [NSString stringWithFormat:@"等待导师收款"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0620"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"不满意要求退款,等待导师的回应"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0500"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"服务中"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1000"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"请进行评价"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"0700"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师同意退款，订单处理中"];
        } else {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"导师不同意退款，订单处理中"];
        }
//        CGSize stateSize3 = [querryCell.alertStateL2.text sizeWithFont:[UIFont systemFontOfSize:12.0f] constrainedToSize:CGSizeMake(200, MAXFLOAT) lineBreakMode:NSLineBreakByWordWrapping];
//        stateSize3.height = querryCell.alertStateL2.frame.size.height;
//        CGRect stateRect3 = [self fitWithLabelForQuerryinfo:querryCell.alertStateL2];
//        querryCell.alertStateL2.frame = CGRectMake(querryCell.moneyL.frame.origin.x + querryCell.moneyL.frame.size.width, querryCell.alertStateL2.frame.origin.y, stateRect3.size.width, stateRect3.size.height);
    } else if (_stateForOrder == OrderStateForComments) {
    
        if (_commentsArray.count == 0) {
            return querryCell;
        }
        ProductOrderModel *tmp = [_commentsArray objectAtIndex:indexPath.row];
        
        [querryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        querryCell.tutorNameL.text = tmp.teacherName;
        querryCell.topicL.text = tmp.title;
        querryCell.moneyL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        if ([[tmp.state substringToIndex:4] isEqualToString:@"1000"] && [[tmp.state substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0900"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"1300"]) {
            
            querryCell.alertStateL.text = [NSString stringWithFormat:@"不同意退款，对本次服务做出评价"];
        } else if ([[tmp.state substringToIndex:4] isEqualToString:@"1000"] && [[tmp.state substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) {
        
            querryCell.alertStateL.text = [NSString stringWithFormat:@"请做出评价"];
        } else {
            
            querryCell.alertStateL.text = [NSString stringWithFormat:@"这次服务已进行评价"];
        }
        //ios6
//        CGSize stateSize3 = [querryCell.alertStateL2.text sizeWithFont:[UIFont systemFontOfSize:12.0f] constrainedToSize:CGSizeMake(200, MAXFLOAT) lineBreakMode:NSLineBreakByWordWrapping];
//        stateSize3.height = querryCell.alertStateL2.frame.size.height;
        //iOS7 以上
//        CGRect stateRect3 = [self fitWithLabelForQuerryinfo:querryCell.alertStateL];
//        querryCell.alertStateL.frame = CGRectMake(querryCell.moneyL.frame.origin.x + querryCell.moneyL.frame.size.width, querryCell.alertStateL.frame.origin.y, stateRect3.size.width, stateRect3.size.height);
    } else {
    
        if (_endArray.count == 0) {
            return querryCell;
        }
        ProductOrderModel *tmp = [_endArray objectAtIndex:indexPath.row];
        
        [querryCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:tmp.teacherUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        querryCell.tutorNameL.text = tmp.teacherName;
        querryCell.topicL.text = tmp.title;
        querryCell.moneyL.text = [NSString stringWithFormat:@"%@元/时", tmp.price];
        
        querryCell.alertStateL.text = [NSString stringWithFormat:@"服务结束"];
        //iOS6
//        CGSize stateSize1 = [querryCell.alertStateL1.text sizeWithFont:[UIFont systemFontOfSize:12.0f] constrainedToSize:CGSizeMake(200, MAXFLOAT) lineBreakMode:NSLineBreakByWordWrapping];
//        stateSize1.height = querryCell.alertStateL1.frame.size.height;
        //iOS7
//        CGRect stateRect1 = [self fitWithLabelForQuerryinfo:querryCell.alertStateL];
//        querryCell.alertStateL.frame = CGRectMake(querryCell.alertStateL.frame.origin.x, querryCell.alertStateL.frame.origin.y, stateRect1.size.width, stateRect1.size.height);
    }
    
    return querryCell;
}
#pragma mark -- 自适应宽度
-(CGRect)fitWithLabelForQuerryinfo:(UILabel *)orglabel
{
    CGRect tmpRect = [orglabel.text boundingRectWithSize:CGSizeMake(200, MAXFLOAT) options:NSStringDrawingUsesLineFragmentOrigin|NSStringDrawingUsesFontLeading attributes:@{NSFontAttributeName:orglabel.font} context:nil];
    tmpRect.size.height = orglabel.frame.size.height;
    return tmpRect;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    detailQuerryInfoViewController *detailQuerryInfoVC = [[detailQuerryInfoViewController alloc]init];
    detailQuerryInfoVC.tagforColor = self.tagColor;
    if (self.tagColor == 0) {
        
        if (_noPayArray.count == 0) {
            
        } else {
            
            detailQuerryInfoVC.orderForDetailM = [self.noPayArray objectAtIndex:indexPath.row];
        }
    } else if (self.tagColor == 1) {
    
        if (_waitEnsureArray.count == 0) {
            
        } else {
            
            detailQuerryInfoVC.orderForDetailM = [self.waitEnsureArray objectAtIndex:indexPath.row];
        }
    } else if (self.tagColor == 2) {
    
        if (_ensuredArray.count == 0) {
            
        } else {
        
            detailQuerryInfoVC.orderForDetailM = [self.ensuredArray objectAtIndex:indexPath.row];
        }
    } else if (self.tagColor == 3) {
    
        if (_ensureTimeArray.count == 0) {
            
        } else {
        
            detailQuerryInfoVC.orderForDetailM = [self.ensureTimeArray objectAtIndex:indexPath.row];
        }
    } else if (self.tagColor == 4) {
    
        if (_servingArray.count == 0) {
            
        } else {
        
            detailQuerryInfoVC.orderForDetailM = [self.servingArray objectAtIndex:indexPath.row];
        }
    } else if (self.tagColor == 5) {
    
        if (_commentsArray.count == 0) {
            
        } else {
        
            detailQuerryInfoVC.orderForDetailM = [self.commentsArray objectAtIndex:indexPath.row];
        }
    } else {
    
        if (_endArray.count == 0) {
            
        } else {
        
            detailQuerryInfoVC.orderForDetailM = [self.endArray objectAtIndex:indexPath.row];
        }
    }
    [self.navigationController pushViewController:detailQuerryInfoVC animated:YES];
}

#pragma mark -- detailQuerryInfoViewDelegate
-(void)toRefreshQuerryInfoView
{
    //[self.querryInfoTV reloadData];
}

#pragma mark -- 请求数据
-(void)getOrderDetailForClient:(NSInteger)state
{
    if (state == OrderStateForNoPay) {
        
        [self orderDetailHandler:@"0100|0200" withArray:_noPayArray];
    }
    if (state == OrderStateForWaitEnsure) {
        
        [self orderDetailHandler:@"0300|0700,0300" withArray:_waitEnsureArray];
    }
    if (state == OrderStateForEnsured) {
        
        [self orderDetailHandler:@"0400|1500,0400|0700,1500,0400|1300,1500,0400" withArray:_ensuredArray];
    }
    if (state == OrderStateForEnsureTime) {
        
        [self orderDetailHandler:@"0500|1500,0500|0700,1500,0500|1300,1500,0500" withArray:_ensureTimeArray];
    }
    if (state == OrderStateForServing) {
        
        [self orderDetailHandler:@"0500|0900,0500|0620|0700,0620|0900,1300,0620" withArray:_servingArray];
    }
    if (state == OrderStateForComments) {
        
        [self orderDetailHandler:@"1000|1010" withArray:_commentsArray];
    }
    if (state == OrderStateForEnd) {
        
        [self orderDetailHandler:@"0800|1400" withArray:_endArray];
    }
}

-(void)orderDetailHandler:(NSString *)state withArray:(NSMutableArray *)array
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderDetail:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withState:state withPage:[NSString stringWithFormat:@"%ld", _nextPage]] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            NSArray *dataArray = [[dic objectForKey:@"data"] objectFromJSONString];
            
            if (_nextPage == 1) {
                [self.noPayArray removeAllObjects];
                [self.waitEnsureArray removeAllObjects];
                [self.ensuredArray removeAllObjects];
                [self.ensureTimeArray removeAllObjects];
                [self.servingArray removeAllObjects];
                [self.commentsArray removeAllObjects];
                [self.endArray removeAllObjects];
            }
            
            for (NSMutableDictionary *tmpDic in dataArray) {
                
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
                
                [array addObject:orderM];
            }
        }
        [self.querryInfoTV reloadData];
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

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if (self.navigationController.viewControllers.count == 1) {
        self.tabBarController.tabBar.hidden = NO;
        
        self.querryInfoTV.frame = CGRectMake(0, 40 + NAVIGATIONHEIGHT + 10, WIDTH, HEIGHT - 40 - 90);
        self.backBT.frame = CGRectZero;
    } else {
    
        self.tabBarController.tabBar.hidden = YES;
        self.querryInfoTV.frame = CGRectMake(0, NAVIGATIONHEIGHT + 10 + 40, WIDTH, HEIGHT - 40 - 90 + 49);
        self.backBT.frame = CGRectMake(25, 30 + 8, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    }
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.querryInfoTV headerBeginRefreshing];
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        //[self getOrderDetail];
    }
}

#pragma mark -- 下拉刷新 & 上拉加载
-(void)headerRefreshing {
    
    self.isUploading = NO;//标记为下拉操作
    self.nextPage = 1;
    
    [self.noPayArray removeAllObjects];
    [self.waitEnsureArray removeAllObjects];
    [self.ensuredArray removeAllObjects];
    [self.ensureTimeArray removeAllObjects];
    [self.servingArray removeAllObjects];
    [self.commentsArray removeAllObjects];
    [self.endArray removeAllObjects];
    
    [self getOrderDetailForClient:_stateForOrder];//重新请求数据
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.querryInfoTV reloadData];
        
        [self.querryInfoTV headerEndRefreshing];
//    });
}

-(void)footerRefreshing {

    self.nextPage ++;
    self.isUploading = YES;
    
    [self getOrderDetailForClient:_stateForOrder];
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.querryInfoTV reloadData];
        
        [self.querryInfoTV footerEndRefreshing];
//    });
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
