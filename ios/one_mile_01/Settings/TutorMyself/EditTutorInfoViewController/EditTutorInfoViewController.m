//
//  EditTutorInfoViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EditTutorInfoViewController.h"

@interface EditTutorInfoViewController ()

@property (nonatomic, strong) UILabel *label5; //见过人数
@property (nonatomic, strong) UILabel *label7; //常驻地
@property (nonatomic, strong) UILabel *label8; //想约
@property (nonatomic, strong) UILabel *label11; //可预约次数
@property (nonatomic, strong) UILabel *label13; //价格
@property (nonatomic, strong) UILabel *label14; //标题
@property (nonatomic, strong) UILabel *label15; //内容
@property (nonatomic, strong) UILabel *label16; //关于ta
@property (nonatomic, strong) UIImageView *imageView6; //关于ta图片
@property (nonatomic, strong) UILabel *label17; //关于ta 相关内容
@property (nonatomic, strong) UIImageView *imageView5;
@property (nonatomic, strong) UILabel *label18;    //学员评价
@property (nonatomic, strong) UITableView *tableView1;//学员评价
@property (nonatomic, strong) UIButton *button3;//查看更多评价
@property (nonatomic, copy) NSString *tutorPhoto;
@property (nonatomic, strong)UIImageView *weixinImageV; //微信图标
@property (nonatomic, strong)UIImageView *SImageV; //S图标
@property (nonatomic, strong)UIImageView *phoneImageV; //电话图标

@property(nonatomic, strong)editTutorInfoModel *editTutorMo;

@end

@implementation EditTutorInfoViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.commentArray = [NSMutableArray array];
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.scrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y + NAVIGATIONHEIGHT,self.view.frame.size.width ,self.view.frame.size.height)];
    self.scrollView.backgroundColor = [UIColor whiteColor];
    self.scrollView.contentSize = CGSizeMake(0,667 * 3);
    self.scrollView.bounces = NO;
    self.scrollView.showsVerticalScrollIndicator = NO;
    //self.scrollView.pagingEnabled = YES;
    self.scrollView.userInteractionEnabled = YES;
    [self.view addSubview:self.scrollView];
    self.navigationController.navigationBar.hidden = YES;

    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(18, 38, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backButton) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];

    // 设置按钮
    UIButton *settingButton = [UIButton buttonWithType:UIButtonTypeCustom];
    settingButton.frame = CGRectMake(self.view.frame.size.width - 60, 32, 32, 32);
    [settingButton setBackgroundImage:[UIImage imageNamed:@"editSetting.png"] forState:UIControlStateNormal];
    settingButton.backgroundColor = [UIColor clearColor];
    [settingButton addTarget:self action:@selector(settingButtonAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:settingButton];
    
    //导师主页
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(WIDTH / 2.0 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
    titleLabel.text = @"导师主页";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.textColor = [UIColor  colorWithRed:190 / 255.0 green:190 / 255.0 blue:190 / 255.0 alpha:1.0];
    [self.view addSubview:titleLabel];
    
    
    //    头像
    self.imageView = [[UIImageView alloc]initWithFrame:CGRectMake(20, 5, 100, 100)];
    self.imageView.backgroundColor = [UIColor redColor];
    //设置圆角
    self.imageView.layer.masksToBounds = YES;
    self.imageView.layer.cornerRadius = 50;
    //边框宽度
    self.imageView.layer.borderWidth = 0;
    [self.scrollView addSubview:self.imageView];

    //    姓名
    self.nameLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView.frame.origin.x + 120, self.imageView.frame.origin.y + 10 , 80, 30)];
    self.nameLabel.text = @"导师姓名";
    self.nameLabel.font = [UIFont systemFontOfSize:22];
    self.nameLabel.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:self.nameLabel];
    
//    微信图标
    self.weixinImageV = [[UIImageView alloc]initWithFrame:CGRectMake(self.nameLabel.frame.origin.x + self.nameLabel.frame.size.width + 10, self.nameLabel.frame.origin.y ,25, 30)];
    self.weixinImageV.image = [UIImage imageNamed:@"consult_chat_unselected.png"];
    [self.scrollView addSubview:self.weixinImageV];
    
//    S图标
    self.SImageV = [[UIImageView alloc]initWithFrame:CGRectMake(self.weixinImageV.frame.origin.x + self.weixinImageV.frame.size.width + 5, self.weixinImageV.frame.origin.y, self.weixinImageV.frame.size.width, self.weixinImageV.frame.size.height)];
    self.SImageV.image = [UIImage imageNamed:@"consult_s_unselected.png"];
    [self.scrollView addSubview:self.SImageV];
    
//    电话图标
    self.phoneImageV = [[UIImageView alloc]initWithFrame:CGRectMake(self.SImageV.frame.origin.x + self.SImageV.frame.size.width + 5, self.SImageV.frame.origin.y, self.SImageV.frame.size.width, self.SImageV.frame.size.height)];
    self.phoneImageV.image = [UIImage imageNamed:@"consult_tel_unselected.png"];
    [self.scrollView addSubview:self.phoneImageV];
    
    
    //    介绍
    self.label2 = [[UILabel alloc]initWithFrame:CGRectMake(self.nameLabel.frame.origin.x, self.nameLabel.frame.origin.y + self.nameLabel.frame.size.height +  10 , self.view.frame.size.width - self.nameLabel.frame.origin.x - 10, 30)];
    self.label2.text = @"新媒体文化传播创始人";
    self.label2.textAlignment = NSTextAlignmentCenter;
    self.label2.textColor = [UIColor colorWithRed:67 / 255.0 green:172 / 255.0 blue:226 /255.0 alpha:1.0];
    [self.scrollView addSubview:self.label2];
    
    //    两个横线
    UILabel *label3 = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.origin.x + 10, self.imageView.frame.origin.y + self.imageView.frame.size.height + 10, self.view.frame.size.width - 20, 0.5)];
    label3.backgroundColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    [self.scrollView addSubview:label3];
    
    UILabel *label4 = [[UILabel alloc]initWithFrame:CGRectMake(label3.frame.origin.x,label3.frame.origin.y + 27,label3.frame.size.width, label3.frame.size.height)];
    label4.backgroundColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    [self.scrollView addSubview:label4];
    //常驻地址图片
    UIImageView *imageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(label3.frame.origin.x + 2, label3.frame.origin.y + 6, 20, 16)];
    imageView2.image = [UIImage imageNamed:@"tutorHome_position.png"];
    imageView2.backgroundColor = [UIColor clearColor];
    [self.scrollView addSubview:imageView2];
    
    //    常驻纽约
    self.label7 = [[UILabel alloc]initWithFrame: CGRectMake(imageView2.frame.origin.x + imageView2.frame.size.width, imageView2.frame.origin.y + 3, 100, 10)];
    _label7.text = @"常驻纽约";
    _label7.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    _label7.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:_label7];
    
    //    想约心形图片
    UIImageView *imageView3 = [[UIImageView alloc]initWithFrame:CGRectMake(_label7.frame.origin.x + _label7.frame.size.width , imageView2.frame.origin.y , imageView2.frame.size.width, imageView2.frame.size.height)];
    if (iPhone5) {
        imageView3.frame = CGRectMake(_label7.frame.origin.x + _label7.frame.size.width - 10 , imageView2.frame.origin.y , imageView2.frame.size.width, imageView2.frame.size.height + 4);
    }else if (iPhone6){
        imageView3.frame = CGRectMake(_label7.frame.origin.x + _label7.frame.size.width + 20 , imageView2.frame.origin.y - 1, imageView2.frame.size.width, imageView2.frame.size.height + 4);
    }
    
    imageView3.image = [UIImage imageNamed:@"tutorHome_favor.png"];
    imageView3.backgroundColor = [UIColor clearColor];
    [self.scrollView addSubview:imageView3];
    //    15
    self.label8 = [[UILabel alloc]initWithFrame: CGRectMake(imageView3.frame.origin.x + imageView3.frame.size.width + 5, imageView3.frame.origin.y + 4, 10, 10)];
    _label8.text = @"15";
    _label8.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    _label8.font = [UIFont systemFontOfSize:10];
    [self.scrollView addSubview:_label8];
    
    //    人想见
    UILabel *label9 = [[UILabel alloc]initWithFrame: CGRectMake(_label8.frame.origin.x + _label8.frame.size.width, _label8.frame.origin.y, 40, 10)];
    label9.text = @"人想见";
    label9.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label9.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label9];
    
    
    //    本周可预约图片
    UIImageView *imageView4 = [[UIImageView alloc]initWithFrame:CGRectMake(label9.frame.origin.x +label9.frame.size.width + 5, imageView3.frame.origin.y , imageView3.frame.size.width, imageView3.frame.size.height)];
    if (iPhone6P) {
        imageView4.frame = CGRectMake(label9.frame.origin.x +label9.frame.size.width + 40, imageView3.frame.origin.y , imageView3.frame.size.width, imageView3.frame.size.height);
    }else if (iPhone6){
        imageView4.frame = CGRectMake(label9.frame.origin.x +label9.frame.size.width + 25, imageView3.frame.origin.y , imageView3.frame.size.width + 2, imageView3.frame.size.height);
    }
    imageView4.image = [UIImage imageNamed:@"tutorHome_times.png"];
    imageView4.backgroundColor = [UIColor clearColor];
    [self.scrollView addSubview:imageView4];
    //    本周可预约
    UILabel *label10 = [[UILabel alloc]initWithFrame: CGRectMake(imageView4.frame.origin.x + imageView4.frame.size.width + 3, imageView4.frame.origin.y + 4, 60, 10)];
    label10.text = @"本周可预约";
    label10.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label10.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label10];
    
    //    5
    self.label11 = [[UILabel alloc]initWithFrame: CGRectMake(label10.frame.origin.x + label10.frame.size.width + 2, label10.frame.origin.y, 8, 10)];
    _label11.text = @"5";
    _label11.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    _label11.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:_label11];
    //    次
    UILabel *label12 = [[UILabel alloc]initWithFrame: CGRectMake(_label11.frame.origin.x + _label11.frame.size.width , _label11.frame.origin.y, 12, 10)];
    label12.text = @"次";
    label12.textColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    label12.font = [UIFont systemFontOfSize:12];
    [self.scrollView addSubview:label12];
    
    //    蓝色部分教你如何利用互联网创业
    self.imageView5 = [[UIImageView alloc]initWithFrame:CGRectMake(label3.frame.origin.x, label12.frame.origin.y + label12.frame.size.height + 20,self.view.frame.size.width - label3.frame.origin.x * 2, 230)];
    self.imageView5.userInteractionEnabled = YES;
    self.imageView5.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    self.imageView5.layer.cornerRadius = 10;
    [self.scrollView addSubview:self.imageView5];
    //    200元/次
    self.label13 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView5.frame.origin.x + 15,self.imageView5.frame.origin.y + 15,100,25)];
    _label13.layer.masksToBounds = YES;
    _label13.layer.cornerRadius = 12;
    _label13.text = @"200 元/次";
    _label13.textAlignment = NSTextAlignmentCenter;
    _label13.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    _label13.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:_label13];
    
    //    文章标题
    self.label14 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView5.frame.size.width / 2 - 167, 50 , 334, 25)];
    [self.imageView5 addSubview:_label14];
    [self.label14 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.imageView5.mas_top).offset(45);
        make.left.equalTo(self.imageView5.mas_left).offset(5);
        make.right.equalTo(self.imageView5.mas_right).offset(-5);
        make.height.offset(30);
    }];
    
    _label14.text = @"教你如何利用互联网就业";
    self.label14.textAlignment = NSTextAlignmentCenter;
    _label14.textColor = [UIColor whiteColor];
    if (iPhone6) {
        _label14.font = [UIFont systemFontOfSize:20 weight:8];
    }else if(iPhone5){
        _label14.font = [UIFont systemFontOfSize:18 weight:8];
    }else{
        _label14.font = [UIFont systemFontOfSize:20 weight:8];
    }
    
    //    文章内容
    self.label15 = [[UILabel alloc]init];
    [self.imageView5 addSubview:_label15];
    [self.label15 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.imageView5.mas_top).offset(80);
        make.left.equalTo(self.imageView5.mas_left).offset(5);
        make.right.equalTo(self.imageView5.mas_right).offset(-5);
        make.height.offset(100);
    }];
    _label15.text = @"教你如何利用互联网就业";
    _label15.textColor = [UIColor whiteColor];
    _label15.numberOfLines = 4;
    
    //    白线
    UILabel *label20 = [[UILabel alloc]init];
    [self.imageView5 addSubview:label20];
    [label20 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.imageView5.mas_top).offset(180);
        make.left.equalTo(self.imageView5.mas_left).offset(5);
        make.right.equalTo(self.imageView5.mas_right).offset(-5);
        make.height.offset(1);
    }];
    label20.backgroundColor = [UIColor whiteColor];
    
    
    //    展示更多按钮
    UIButton *button5 = [[UIButton alloc]initWithFrame:CGRectMake(_label15.frame.origin.x, _label15.frame.origin.y + _label15.frame.size.height + 12, 60, 33)];
    [self.imageView5 addSubview:button5];
    [button5 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.imageView5.mas_left).offset(20);
        make.bottom.equalTo(self.imageView5.mas_bottom).offset(-10);
        make.height.offset(33);
        make.width.offset (60);
    }];
    [button5 setBackgroundImage:[UIImage imageNamed:@"tutorHome_more.png"] forState:UIControlStateNormal];
    [button5 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [button5 addTarget:self action:@selector(editTutorShowMorebuttonAction:) forControlEvents:UIControlEventTouchUpInside];
    
    
    //2小时
    UILabel *label21 = [[UILabel alloc]init];
    [self.imageView5 addSubview:label21];
    [label21 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.imageView5.mas_bottom).offset(-12);
        make.right.equalTo(self.imageView5.mas_right).offset(-20);
        make.height.offset(25);
        make.width.offset(50);
    }];
    label21.text = @"2小时";
    label21.textColor = [UIColor whiteColor];
    label21.font = [UIFont systemFontOfSize:17];
    
    
    //    关于ta
    self.label16 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView5.frame.origin.x,self.imageView5.frame.origin.y + self.imageView5.frame.size.height + 15,100,25)];
    _label16.text = @"关于 ta";
    _label16.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    _label16.font = [UIFont systemFontOfSize:22];
    [self.scrollView addSubview:_label16];
    
    
    //    导师评价
    self.label17 = [[UILabel alloc]initWithFrame:CGRectMake(self.label16.frame.origin.x,self.label16.frame.origin.y + self.label16.frame.size.height + 10,self.view.frame.size.width - self.label16.frame.origin.x * 2,300)];
    _label17.text = @"待定";
    _label17.numberOfLines = 0;
    _label17.font = [UIFont systemFontOfSize:16];
    [self.scrollView addSubview:_label17];
    
    
    //    学员评价
    self.label18 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView5.frame.origin.x,self.label17.frame.origin.y + _label17.frame.size.height + 10,100,25)];
    self.label18.text = @"学员评价";
    self.label18.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:223/255.0 alpha:1.0];
    self.label18.font = [UIFont systemFontOfSize:22];
    [self.scrollView addSubview:self.label18];
    
    
    //    学员评价tableView
    self.tableView1 = [[UITableView alloc]initWithFrame:CGRectMake(self.label18.frame.origin.x, self.label18.frame.origin.y + self.label18.frame.size.height - 20, self.label17.frame.size.width, 0) style:UITableViewStylePlain];
    self.tableView1.delegate = self;
    self.tableView1.dataSource = self;
    self.tableView1.backgroundColor = [UIColor blackColor];
    self.tableView1.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.scrollView addSubview:self.tableView1];

    //    查看更多评价按钮
    self.button3 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 80, self.tableView1.frame.origin.y + self.tableView1.frame.size.height + 20, 160, 35)];
    self.button3.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    self.button3.layer.cornerRadius = 5;
    [self.button3 setTitle:@"查看更多评价" forState:UIControlStateNormal];
    [self.button3 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.button3 addTarget:self action:@selector(tutorWatchMoreAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.scrollView addSubview:self.button3];

    [self getDetailData];

    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(backgroundTapped:)];
    [self.view addGestureRecognizer:resignKeyboard];

    // Do any additional setup after loading the view.
}


-(void)settingButtonAction{
    
    [UIView animateWithDuration:0.5 delay:0.5 usingSpringWithDamping:10 initialSpringVelocity:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        
        self.editTutorInfoSettingV = [[editTutorInfoSettingView alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, self.view.frame.size.height)];
        self.editTutorInfoSettingV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
        self.editTutorInfoSettingV.editTutorM = self.editTutorMo;
        self.editTutorInfoSettingV.myDelegate = self;
        [self.view addSubview:self.editTutorInfoSettingV];
        
        self.editTutorInfoSettingV.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height);
        
    } completion:^(BOOL finished) {
        
        NSLog(@"动画完成");
        
    }];
}

#pragma mark -- tapGestureAction
//撤销键盘
-(void)backgroundTapped:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}

#pragma mark -- buttonAction
//查看更多按钮
-(void)tutorWatchMoreAction:(UIButton *)button{
    
    tutorCommentViewController *tutorCommentVC = [[tutorCommentViewController alloc]init];
    tutorCommentVC.DetialID = self.toDetialID;
    tutorCommentVC.photoURLForComments = self.tutorPhoto;
    [self.navigationController pushViewController:tutorCommentVC animated:YES];
}

-(void)editTutorShowMorebuttonAction:(UIButton *)button{
    
    showMoreViewController *editTutorShowMoreVC = [[showMoreViewController alloc]init];
    editTutorShowMoreVC.contentLabel = [[UILabel alloc]init];
    editTutorShowMoreVC.titleLabel = [[UILabel alloc]init];
    editTutorShowMoreVC.titleLabel.text = self.label14.text;
    editTutorShowMoreVC.contentLabel.text = self.editTutorMo.serviceContent;
    
    [self.navigationController pushViewController:editTutorShowMoreVC animated:YES];
}

#pragma mark -- editTutorInfoSettingViewDelegate
-(void)cancelEditTutorInfoButton
{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.editTutorInfoSettingV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
        
    }];
}

-(void)timeoutToLogin
{
    
    LoginViewController *loginVC = [[LoginViewController alloc] init];
    loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
    [self presentViewController:loginVC animated:YES completion:^{
        
    }];
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    
    [super viewWillAppear:animated];
    
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewWillDisappear:(BOOL)animated
{
    
    [super viewWillDisappear:animated];
    
    if (self.navigationController.viewControllers.count == 1){
        
        self.tabBarController.tabBar.hidden = NO;
    }else{
        self.tabBarController.tabBar.hidden = YES;
    }
}

#pragma mark -- 获取数据
-(void)getDetailData
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForTeacherDetail:self.toDetialID] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        //NSLog(@"result = %@",result);
    
        
        NSDictionary *dic = (NSDictionary *)result;
        //NSLog(@"detailDic = %@", dic);
        self.label2.text = [dic objectForKey:@"position"];
        self.label7.text = [NSString stringWithFormat:@"常驻%@", [dic objectForKey:@"address"]];
        self.nameLabel.text = [dic objectForKey:@"name"];

        [self.imageView sd_setImageWithURL:[NSURL URLWithString:[dic objectForKey:@"iconUrl"]]];
        self.tutorPhoto = [dic objectForKey:@"iconUrl"];
        [self.imageView6 sd_setImageWithURL:[NSURL URLWithString:[dic objectForKey:@"iconUrl"]]];
        
        self.label8.text = [dic objectForKey:@"likeNo"];
        self.label11.text = [dic objectForKey:@"timeperweek"];
        
        self.label13.text = [NSString stringWithFormat:@"%@ 元/次", [dic objectForKey:@"price"]];
        self.label14.text = [dic objectForKey:@"serviceTitle"];
        self.label15.text = [dic objectForKey:@"serviceContent"];
        self.editTutorMo.serviceContent = self.label15.text;
        
        self.label17.text = [dic objectForKey:@"introduce"];
        _label15.font = [UIFont systemFontOfSize:16];
        
        self.editTutorMo = [[editTutorInfoModel alloc]init];

        self.editTutorMo.address =  [NSString stringWithFormat:@"常驻%@", [dic objectForKey:@"address"]];
        self.editTutorMo.timeperweek = [dic objectForKey:@"timeperweek"];
        
        //    字符串截取
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"<h1 style:\"height:14px;line-height: 14px;display: inline-block;font-family:\"hiragino sans gb\",\"microsoft  yahei\",\"wenquanyi micro hei\",arial,helvetica,sans-serif;font-size: 16px;color: black;margin: 10px;margin-top: 15px;  font-weight: bold;\" >" withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"<h1 style:\"height: 14px;line-height: 14px;display: inline-block;font-family: \"Hiragino Sans GB\",\"Microsoft YaHei\",\"WenQuanYi Micro Hei\",Arial,Helvetica,sans-serif;font-size: 16px;color: black;margin: 10px;margin-top: 15px; font-weight: bold;\">" withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"</h1>" withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"<br>" withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"<li>" withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"</li>" withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"<div class=\"disc\">"  withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"<div class=”disc”>"  withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"<div>" withString:@""];
        self.label15.text = [self.label15.text stringByReplacingOccurrencesOfString:@"</div>" withString:@""];
        
        if ([self.label15.text rangeOfString:@"\n"].location != NSNotFound)  {
            self.label15.text = [NSString stringWithFormat:@"       %@",self.label15.text];
            
        }
        [self frameSize];
        [self getEditTutorInfoTableViewData];
    }];
}

-(void)getEditTutorInfoTableViewData{
    
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";

    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForTeacherCommentList:self.toDetialID withPage:@"1"] connectBlock:^(id data) {
        
        NSString *jsonStr = [[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil] objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[jsonStr objectFromJSONString];
        
        if (jsonArray.count != 0) {
            
            for (NSMutableDictionary *dic in jsonArray) {
                
                editTutorAppraiseModel *editTutorM = [[editTutorAppraiseModel alloc] init];
                
                editTutorM.commentId = [dic objectForKey:@"commentId"];
                editTutorM.content = [dic objectForKey:@"content"];
                editTutorM.score = [dic objectForKey:@"score"];
                editTutorM.createTime = [dic objectForKey:@"createTime"];
                editTutorM.nickName = [dic objectForKey:@"nickName"];
                editTutorM.iconUrl = [dic objectForKey:@"iconUrl"];
                editTutorM.serviceTitle = [dic objectForKey:@"serviceTitle"];
                
                [self.commentArray addObject:editTutorM];
            }
        }
        
        if (self.commentArray.count == 0) {
            self.tableView1.frame = CGRectMake(0, 0, 0, 0);
            UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(self.label18.frame.origin.x, self.label18.frame.origin.y + self.label18.frame.size.height + 10, self.imageView5.frame.size.width, 50)];
            label.text = @"暂无数据哦。。。亲";
            [self.scrollView addSubview:label];
            self.button3.frame = CGRectMake(0,0, 0, 0);
            self.scrollView.contentSize = CGSizeMake(0, label.frame.origin.y + label.frame.size.height + 30);
        }else{
            
            if (_commentArray.count > 3) {
                
                self.tableView1.frame = CGRectMake(self.label18.frame.origin.x, self.label18.frame.origin.y + self.label18.frame.size.height + 20, self.label17.frame.size.width, 150 * 3);
                
            }else{
                
                self.tableView1.frame = CGRectMake(self.label18.frame.origin.x, self.label18.frame.origin.y + self.label18.frame.size.height + 20, self.label17.frame.size.width, 150 *self.commentArray.count);
            }
            
            self.button3.frame = CGRectMake(self.view.frame.size.width / 2 - 80,self.tableView1.frame.origin.y + self.tableView1.frame.size.height + 20 , 160, 35);
            
            self.scrollView.contentSize = CGSizeMake(0, self.button3.frame.origin.y + self.button3.frame.size.height + 50);
        }
        [self.tableView1 reloadData];
    }];

}


#pragma mark -- fitToHeightForCell

+(CGFloat)heightForcell:(NSString *)content
{
    //参数1.设置计算内容对的宽和高
    //参数2.设置计算高度模式
    //参数3.设置计算字体大小属性
    //参数4.系统备用参数,设置为nil
    
    //其中宽度一定与显示内容的label宽度一致,否则计算不准确
    CGSize size = CGSizeMake([UIScreen mainScreen].bounds.size.width - 20 , 10000);
    
    //字体大小一定与显示内容的label字体大小一致
    NSDictionary *dic = [NSDictionary dictionaryWithObject:[UIFont systemFontOfSize:16.0] forKey:NSFontAttributeName];
    
    CGRect frame = [content boundingRectWithSize:size options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];
    return frame.size.height;
}

//自适应高度
-(void)frameSize{
    
    self.label17.frame = CGRectMake(self.label16.frame.origin.x,self.label16.frame.origin.y + self.label16.frame.size.height + 10,self.view.frame.size.width - self.label16.frame.origin.x * 2,[EditTutorInfoViewController heightForcell:self.label17.text]);
    self.label17.text = [self.label17.text stringByReplacingOccurrencesOfString:@"</h1>" withString:@""];
    self.label17.text = [self.label17.text stringByReplacingOccurrencesOfString:@"<br>" withString:@""];
    
    self.label18.frame = CGRectMake(self.label16.frame.origin.x,self.label17.frame.origin.y + self.label17.frame.size.height + 10,self.view.frame.size.width,self.label16.frame.size.height);
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    if (_commentArray.count > 3) {
        return 3;
    }else{
        return _commentArray.count;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 150;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"myCell";
    editTutorInfoTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    
    if (cell == nil) {
        cell = [[editTutorInfoTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
    }
    editTutorAppraiseModel *tmp = [_commentArray objectAtIndex:indexPath.row];
    cell.label1.text = tmp.content;
    cell.label2.text = tmp.serviceTitle;
    [cell.imageView1 sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl]];
    cell.label3.text = tmp.nickName;
    cell.label4.text = tmp.createTime;

    return cell;
}

-(void)backButton{
    
        [self.navigationController popToRootViewControllerAnimated:YES];
}



- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
