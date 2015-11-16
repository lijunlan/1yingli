//
//  tutorHomeViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/24.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorHomeViewController.h"
#import "TutorHomeModal.h"
#import "appraiseTutorModal.h"

#import "AFNConnect.h"
#import "JSONKit.h"

#import <UIImageView+WebCache.h>

@interface tutorHomeViewController ()<UIScrollViewDelegate>
{
    BOOL isCollected;
    CGRect imageRect;
    UIView *markView;
    UIScrollView *scrollImageView;
    UIImageView *scrollIV;
}

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
@property (nonatomic, strong) UIImageView *consultIV1;
@property (nonatomic, strong) UIImageView *consultIV2;
@property (nonatomic, strong) UIImageView *consultIV3;

//评价数组
@property (nonatomic, strong) NSMutableArray *commentArray;

//导师头像
@property (nonatomic, copy) NSString *tutorPhoto;

@property (nonatomic, strong) TutorHomeModal *tutorM;

@end

@implementation tutorHomeViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.commentArray = [NSMutableArray array];
        self.tutorM = [[TutorHomeModal alloc] init];
    }
    
    return self;
}

- (void)viewDidLoad {
    
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    
    //加载等待视图
    self.panelView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, WIDTH, HEIGHT)];
    self.panelView.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
    self.panelView.backgroundColor = [UIColor blackColor];
//    self.panelView.alpha = 0.0f;
    [self.view addSubview:_panelView];
    /*
    self.loadingView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    self.loadingView.frame = CGRectMake((self.view.frame.size.width - self.loadingView.frame.size.width) / 2, (self.view.frame.size.height - self.loadingView.frame.size.height) / 2, self.loadingView.frame.size.width, self.loadingView.frame.size.height);
    self.loadingView.autoresizingMask = UIViewAutoresizingFlexibleBottomMargin | UIViewAutoresizingFlexibleLeftMargin | UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleTopMargin;
    [self.panelView addSubview:self.loadingView];
     */
    markView = [[UIView alloc] initWithFrame:_panelView.bounds];
    markView.backgroundColor = [UIColor blackColor];
    markView.alpha = 0.0;
    [_panelView addSubview:markView];
    
    UIGestureRecognizer *tapView = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tappedWithImage:)];
    [self.panelView addGestureRecognizer:tapView];
    
    scrollImageView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT)];
    [_panelView addSubview:scrollImageView];
    scrollImageView.backgroundColor = [UIColor clearColor];
    scrollImageView.bouncesZoom = YES;
    scrollImageView.minimumZoomScale = 1.0f;
    scrollImageView.delegate = self;
    scrollImageView.contentSize = scrollImageView.frame.size;
    
    self.mainView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, WIDTH, HEIGHT)];
    self.mainView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:_mainView];
    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backButton) forControlEvents:UIControlEventTouchUpInside];
    [_mainView addSubview:button];
    
    //    title
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 80, 35, 160, 40)];
    titleL.text = @"导师主页";
    titleL.textAlignment = NSTextAlignmentCenter;
    titleL.textColor = [UIColor lightGrayColor];
    titleL.font = [UIFont systemFontOfSize:19.0f];
    [_mainView addSubview:titleL];
    
    self.scrollView = [[UIScrollView alloc]init];
    [_mainView addSubview:self.scrollView];
    [self.scrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(NAVIGATIONHEIGHT);
        make.right.equalTo(self.view.mas_right).offset(0);
        make.width.offset(self.view.frame.size.width);
        make.height.offset([UIScreen mainScreen].bounds.size.height);
    }];
    self.scrollView.backgroundColor = [UIColor whiteColor];
    self.scrollView.contentSize = CGSizeMake(0,667 * 3);
    self.scrollView.bounces = NO;
    self.scrollView.showsVerticalScrollIndicator = NO;
    self.scrollView.userInteractionEnabled = YES;
    self.navigationController.navigationBar.hidden = YES;
    
    //    收藏
    self.button1 = [UIButton buttonWithType:UIButtonTypeCustom];
    [_mainView addSubview:self.button1];
    [self.button1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(42);
        make.right.equalTo(self.view.mas_right).offset(-65);
        make.width.offset(25);
        make.height.offset(24);
    }];
    
    self.button1.backgroundColor = [UIColor clearColor];
    [self.button1 addTarget:self action:@selector(collectionAction:) forControlEvents:UIControlEventTouchUpInside];
    
//    分享
    UIButton *button2 = [UIButton buttonWithType:UIButtonTypeCustom];
    [_mainView addSubview:button2];
    [button2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(40);
        make.right.equalTo(self.view.mas_right).offset(-20);
        make.width.offset(PUSHANDPOPBUTTONSIZE + 5);
        make.height.offset(PUSHANDPOPBUTTONSIZE);
    }];

    [button2 setBackgroundImage:[UIImage imageNamed:@"translation.png"] forState:UIControlStateNormal];
    button2.backgroundColor = [UIColor clearColor];
    [button2 addTarget:self action:@selector(buttonShareAction) forControlEvents:UIControlEventTouchUpInside];
    
//    头像
    self.imageView = [[UIImageView alloc]initWithFrame:CGRectMake(10, 5, 100, 100)];
    //self.imageView.backgroundColor = [UIColor yellowColor];
    self.imageView.image = [UIImage imageNamed:@"placeholders.png"];
    //设置圆角
    self.imageView.layer.masksToBounds = YES;
    self.imageView.layer.cornerRadius = 50;
    //边框宽度
    self.imageView.layer.borderWidth = 0;
    self.imageView.userInteractionEnabled = YES;
    [self.scrollView addSubview:self.imageView];
    
    imageRect = self.imageView.frame;
    
    scrollIV = [[UIImageView alloc] initWithFrame:imageRect];
    scrollIV.contentMode = UIViewContentModeScaleAspectFill;
    scrollIV.clipsToBounds = YES;
    [scrollImageView addSubview:scrollIV];
    
    UIGestureRecognizer *tapImageGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tappedWithImage:)];
    [self.imageView addGestureRecognizer:tapImageGR];
    
//    姓名
    self.nameLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView.frame.origin.x + 120, self.imageView.frame.origin.y + 10 , 100, 30)];
    //self.nameLabel.text = @"杜潇潇";
    self.nameLabel.font = [UIFont systemFontOfSize:22];
    self.nameLabel.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:self.nameLabel];
    
//    联系方式
    self.consultIV1 = [[UIImageView alloc] init];
    [self.scrollView addSubview:_consultIV1];
    [self.consultIV1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.nameLabel.mas_top).offset(0);
        make.right.equalTo(self.nameLabel.mas_right).offset(15);
        make.width.offset(25);
        make.height.offset(30);
    }];
    self.consultIV1.image = [UIImage imageNamed:@"consult_chat_unselected.png"];
    
    self.consultIV2 = [[UIImageView alloc]init];
    [self.scrollView addSubview:_consultIV2];
    [self.consultIV2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.consultIV1.mas_top).offset(0);
        make.right.equalTo(self.consultIV1.mas_right).offset(30);
        make.width.offset(25);
        make.height.offset(30);
    }];
    self.consultIV2.image = [UIImage imageNamed:@"consult_s_unselected.png"];
    
    self.consultIV3 = [[UIImageView alloc]init];
    [self.scrollView addSubview:_consultIV3];
    [self.consultIV3 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.consultIV2.mas_top).offset(0);
        make.right.equalTo(self.consultIV2.mas_right).offset(30);
        make.width.offset(25);
        make.height.offset(30);
    }];
    self.consultIV3.image = [UIImage imageNamed:@"consult_tel_unselected.png"];
    
//    介绍1
    
    self.label2 = [[UILabel alloc]init];
    [self.scrollView addSubview:self.label2];
    [self.label2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.nameLabel.mas_top).offset(35);
        make.left.equalTo(self.nameLabel.mas_left).offset(0);
        make.right.equalTo(self.nameLabel.mas_right).offset(130);
        make.height.offset(30);
    }];
    self.label2.font = [UIFont systemFontOfSize:15.0f];
    self.label2.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
    
//    介绍2

    self.majorL = [[UILabel alloc]init];
    [self.scrollView addSubview:self.majorL];
    [self.majorL mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.label2.mas_top).offset(20);
        make.left.equalTo(self.label2.mas_left).offset(0);
        make.right.equalTo(self.label2.mas_right).offset(0);
        make.height.offset(30);
    }];
    self.majorL.font = [UIFont systemFontOfSize:14.0f];
    self.majorL.textColor = [UIColor colorWithRed:205 / 255.0 green:206 / 255.0 blue:206 / 255.0 alpha:1.0];
 
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
        imageView3.frame = CGRectMake(_label7.frame.origin.x + _label7.frame.size.width - 5 , imageView2.frame.origin.y , imageView2.frame.size.width, imageView2.frame.size.height + 4);
    }else if (iPhone6){
        imageView3.frame = CGRectMake(_label7.frame.origin.x + _label7.frame.size.width + 20 , imageView2.frame.origin.y - 1, imageView2.frame.size.width, imageView2.frame.size.height + 4);
    }else if (iPhone6P){
        imageView3.frame = CGRectMake(_label7.frame.origin.x + _label7.frame.size.width + 35 , imageView2.frame.origin.y - 1, imageView2.frame.size.width, imageView2.frame.size.height + 4);
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
    self.label14 = [[UILabel alloc]init];
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
        _label14.font = [UIFont systemFontOfSize:17 weight:8];
    }else if(iPhone5 || iPhone4s){
        _label14.font = [UIFont systemFontOfSize:15 weight:8];
    }else{
        _label14.font = [UIFont systemFontOfSize:19 weight:8];
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
    self.label15.font = [UIFont systemFontOfSize:14];
    _label15.text = @"教你如何利用互联网就业";
    _label15.textColor = [UIColor whiteColor];
    _label15.numberOfLines = 0;
    
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
    [button5 addTarget:self action:@selector(showMorebuttonAction:) forControlEvents:UIControlEventTouchUpInside];
    
    
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
    self.tableView1.backgroundColor = [UIColor blackColor];
    self.tableView1.delegate = self;
    self.tableView1.dataSource = self;
    self.tableView1.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.scrollView addSubview:self.tableView1];
    
  
//    查看更多评价按钮
    self.button3 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 80, self.tableView1.frame.origin.y + self.tableView1.frame.size.height + 20, 160, 35)];
    self.button3.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    self.button3.layer.cornerRadius = 5;
    [self.button3 setTitle:@"查看更多评价" forState:UIControlStateNormal];
    [self.button3 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.button3 addTarget:self action:@selector(watchMoreAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.scrollView addSubview:self.button3];
    
    //    立即约见
    self.button4 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.origin.x,self.view.frame.size.height - 60 ,self.view.frame.size.width,60)];
    self.button4.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    [self.button4 setTitle:@"       立即约见" forState:UIControlStateNormal];
    [self.button4 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.button4 addTarget:self action:@selector(appointmentMeetAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.mainView addSubview:self.button4];

    UIImageView *weixinImage = [[UIImageView alloc]init];
    [self.button4 addSubview:weixinImage];
    [weixinImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.button4.mas_bottom).offset(-10);
        make.left.equalTo(self.button4.titleLabel.mas_left).offset(-10);
        make.height.offset(30);
        make.width.offset(40);
    }];
    weixinImage.image = [UIImage imageNamed:@"tutorHome_chat.png"];
    
    [self getDetailData];
    [self judgeCollection];
    // Do any additional setup after loading the view.
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

#pragma mark -- 头像放大
-(void)tappedWithImage:(UITapGestureRecognizer *)tap
{
    if (self.imageView.image == nil) {
        return;
    }
    scrollIV.image = self.imageView.image;
    
    if (tap.view == self.panelView) {
        
        [self.view sendSubviewToBack:_panelView];
        scrollImageView.zoomScale = 1.0f;
        
//        [UIView animateWithDuration:0.3f animations:^{
//            
//            markView.alpha = 0.0f;
//        }];
        
        [UIView animateWithDuration:0.5 animations:^{
            
            scrollIV.frame = imageRect;
            
        }];
    } else {
    
        markView.alpha = 1.0f;
        [self.view bringSubviewToFront:_panelView];
        
        [self performSelector:@selector(setAnimation) withObject:nil afterDelay:0.1];
    }
}

-(void)setAnimation
{
    UIImage *image = self.imageView.image;
    
    float tmpFlab = image.size.width / WIDTH;
    
    float tmpHeight = image.size.height / tmpFlab;
    
    scrollImageView.maximumZoomScale = HEIGHT / tmpHeight;
    
    CGRect rect = CGRectMake(0, self.view.bounds.size.height/2 - tmpHeight/2, WIDTH, tmpHeight);
    
    [UIView animateWithDuration:0.3 animations:^{
        scrollIV.frame = rect;
    }];
}

#pragma mark -- scrollViewDelegate
-(UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView
{
    return scrollIV;
}

-(void)scrollViewDidZoom:(UIScrollView *)scrollView
{
    CGSize boundsSize = scrollView.bounds.size;
    CGRect imgFrame = scrollIV.frame;
    CGSize contentSize = scrollView.contentSize;
    
    CGPoint centerPoint = CGPointMake(contentSize.width / 2, contentSize.height / 2);
    
    // center horizontally
    if (imgFrame.size.width <= boundsSize.width) {
        centerPoint.x = boundsSize.width / 2;
    }
    
    // center vertically
    if (imgFrame.size.height <= boundsSize.height) {
        centerPoint.y = boundsSize.height / 2;
    }
    
    scrollIV.center = centerPoint;
}

//立即约见
-(void)appointmentMeetAction:(UIButton *)button{
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        AppointmentOrderViewController *appointmentOrderVC = [[AppointmentOrderViewController alloc]init];
        appointmentOrderVC.pricePertimes = self.label13.text;
        appointmentOrderVC.appointTopic = self.label14.text;
        appointmentOrderVC.teacherIDForOrder = self.toDetialID;
        appointmentOrderVC.contactWay = self.tutorM.talkWay;
        appointmentOrderVC.tutorForAppointment = self.tutorM;
        [self.navigationController  pushViewController:appointmentOrderVC animated:YES];
    } else {
    
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"还没有登录呢。。。" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
        alert.delegate = self;
        [self.view addSubview:alert];
        [alert show];
    }
    
}

-(void)backButton{

    if (self.navigationController.viewControllers.count == 2) {
        
        [self.navigationController popToRootViewControllerAnimated:YES];
    } else if (self.navigationController.viewControllers.count > 2) {
        
        [self.navigationController popToViewController:[self.navigationController.viewControllers objectAtIndex:1] animated:YES];
    }
}

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

#pragma mark -- 数据请求 收藏
-(void)judgeCollection{

    NSData *data = [AFNConnect createDataForisLikeTutor:[[[NSUserDefaults standardUserDefaults]objectForKey:@"userInfo"] objectForKey:@"uid"] andTutorID:self.toDetialID];
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:data connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        NSString *state = [dic objectForKey:@"likeTeacher"];
        
        if ([state isEqualToString:@"true"] && [[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
            
            [self.button1 setBackgroundImage:[UIImage imageNamed:@"collection.png"] forState:UIControlStateNormal];
            
        } else {
            
            [self.button1 setBackgroundImage:[UIImage imageNamed:@"tutorHome_favor.png"] forState:UIControlStateNormal];
            
        }
    }];
}


-(void)getDetailData
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForTeacherDetail:self.toDetialID] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        //NSLog(@"result = %@",result);
        
        NSDictionary *dic = (NSDictionary *)result;
        //NSLog(@"detailDic = %@", dic);
        
        self.tutorM.talkWay = [dic objectForKey:@"talkWay"];
        if (((NSString *)[dic objectForKey:@"talkWay"]).length == 0) {
            
        } else {
        
            NSString *consultStr = [[dic objectForKey:@"talkWay"] substringWithRange:NSMakeRange(1, 8)];
            NSArray *consultArray = [consultStr componentsSeparatedByString:@","];
            
            if ([[consultArray objectAtIndex:0] isEqualToString:@"10"]) {
                
                self.consultIV1.image = [UIImage imageNamed:@"consult_chat_unselected.png"];
            } else {
                
                self.consultIV1.image = [UIImage imageNamed:@"consult_chat_selected.png"];
            }
            
            if ([[consultArray objectAtIndex:1] isEqualToString:@"20"]) {
                
                self.consultIV2.image = [UIImage imageNamed:@"consult_s_unselected.png"];
            } else {
                
                self.consultIV2.image = [UIImage imageNamed:@"consult_s_selected.png"];
            }
            
            if ([[consultArray objectAtIndex:2] isEqualToString:@"30"]) {
                
                self.consultIV3.image = [UIImage imageNamed:@"consult_tel_unselected.png"];
            } else {
                
                self.consultIV3.image = [UIImage imageNamed:@"consult_tel_selected.png"];
            }
        }
        
        self.label2.text = [dic objectForKey:@"simpleShow1"];
        self.tutorM.simpleShow1 = _label2.text;

        self.majorL.text = [dic objectForKey:@"simpleShow2"];
        _tutorM.simpleShow2 = _majorL.text;

        self.label7.text = [NSString stringWithFormat:@"常驻%@", [dic objectForKey:@"address"]];
        self.tutorM.address = _label7.text;
        self.nameLabel.text = [dic objectForKey:@"name"];
        self.tutorM.name = _nameLabel.text;
        [self.imageView sd_setImageWithURL:[NSURL URLWithString:[dic objectForKey:@"iconUrl"]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        _tutorM.iconUrl = [dic objectForKey:@"iconUrl"];
        self.tutorPhoto = [dic objectForKey:@"iconUrl"];
        [self.imageView6 sd_setImageWithURL:[NSURL URLWithString:[dic objectForKey:@"iconUrl"]]];
        
        self.label8.text = [dic objectForKey:@"likeNo"];
        _tutorM.likeNo = _label8.text;
        self.label11.text = [dic objectForKey:@"timeperweek"];
        _tutorM.timeperweek = _label11.text;
        
        self.label13.text = [NSString stringWithFormat:@"%@ 元/次", [dic objectForKey:@"price"]];
        _tutorM.price = [dic objectForKey:@"price"];
//        标题
        self.label14.text = self.serviceTitleStr;
        _tutorM.serviceTitle = _label14.text;
        
        self.label15.text = [dic objectForKey:@"serviceContent"];
        self.contentString = self.label15.text;
        _tutorM.serviceContent = self.label15.text;
        
        self.label17.text = [dic objectForKey:@"introduce"];
        _tutorM.introduce = _label17.text;
        _label17.font = [UIFont systemFontOfSize:16];
        
        [self frameSize];
        [self gettableViewData];

//            字符串截取
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"<h1 style:\"height:14px;line-height: 14px;display: inline-block;font-family:\"hiragino sans gb\",\"microsoft  yahei\",\"wenquanyi micro hei\",arial,helvetica,sans-serif;font-size: 16px;color: black;margin: 10px;margin-top: 15px;  font-weight: bold;\" >" withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"<h1 style:\"height: 14px;line-height: 14px;display: inline-block;font-family: \"Hiragino Sans GB\",\"Microsoft YaHei\",\"WenQuanYi Micro Hei\",Arial,Helvetica,sans-serif;font-size: 16px;color: black;margin: 10px;margin-top: 15px; font-weight: bold;\">" withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"</h1>" withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"<br>" withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"<li>" withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"</li>" withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"<div class=\"disc\">"  withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"<div class=”disc”>"  withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"<div>" withString:@""];
        self.contentString = [self.contentString stringByReplacingOccurrencesOfString:@"</div>" withString:@""];
        
        if ([self.contentString rangeOfString:@"\n" options:NSLiteralSearch].location != NSNotFound)  {
            self.contentString = [NSString stringWithFormat:@"       %@",self.contentString];
        }
        self.label15.text = self.contentString;

    }];
}


-(void)gettableViewData{
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTeacherCommentList:self.toDetialID withPage:@"1"] connectBlock:^(id data) {
        
        NSString *jsonStr = [[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil] objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[jsonStr objectFromJSONString];
        
        if (jsonArray.count != 0) {
            
            for (NSMutableDictionary *dic in jsonArray) {
                
                appraiseTutorModal *appraiseModal = [[appraiseTutorModal alloc] init];
                
                appraiseModal.commentId = [dic objectForKey:@"commentId"];
                appraiseModal.content = [dic objectForKey:@"content"];
                appraiseModal.score = [dic objectForKey:@"score"];
                appraiseModal.createTime = [dic objectForKey:@"createTime"];
                appraiseModal.nickName = [dic objectForKey:@"nickName"];
                appraiseModal.iconUrl = [dic objectForKey:@"iconUrl"];
                appraiseModal.serviceTitle = [dic objectForKey:@"serviceTitle"];
                
                [self.commentArray addObject:appraiseModal];
            }
        }
        if (self.commentArray.count == 0) {
            
            self.tableView1.frame = CGRectMake(0, 0, 0, 0);
            
            UILabel *label = [[UILabel alloc]init];
            [self.scrollView addSubview:label];
            
            [label mas_makeConstraints:^(MASConstraintMaker *make) {
                make.left.equalTo(self.label18.mas_left).offset(0);
                make.top.equalTo(self.label18.mas_top).offset(20);
                make.width.offset(self.imageView5.frame.size.width);
                make.height.offset(50);
                
            }];
            
            [self.scrollView mas_makeConstraints:^(MASConstraintMaker *make) {
                make.top.equalTo(self.view.mas_top).offset(NAVIGATIONHEIGHT);
                make.bottom.equalTo(label.mas_bottom).offset(100);
                make.right.equalTo(self.view.mas_right).offset(0);
                make.width.offset(self.view.frame.size.width);
            }];
            
            label.text = @"暂无数据哦。。。亲";
            self.button3.frame = CGRectMake(0, 0, 0, 0);
            
            [self.scrollView flashScrollIndicators];
            
        }else{
            
            if (_commentArray.count > 3) {
                
                [self frameSize];
                
                self.tableView1.frame = CGRectMake(self.label18.frame.origin.x, self.label18.frame.origin.y + self.label18.frame.size.height + 20, self.label17.frame.size.width, 150 * 3);
                
            }else{
                
                [self frameSize];
                
                self.tableView1.frame = CGRectMake(self.label18.frame.origin.x, self.label18.frame.origin.y + self.label18.frame.size.height + 20, self.label17.frame.size.width, 150 * self.commentArray.count);
            }
            
            self.button3.frame = CGRectMake(self.view.frame.size.width / 2 - 80,self.tableView1.frame.origin.y + self.tableView1.frame.size.height + 20 , 160, 35);
            
            self.scrollView.contentSize = CGSizeMake(0, self.button3.frame.origin.y + self.button3.frame.size.height + self.button4.frame.size.height + 50);
            
            [self.scrollView flashScrollIndicators];
            
        }
        
        [self.tableView1 reloadData];
        
    }];

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
    appraiseTabelViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    
    if (cell == nil) {
        cell = [[appraiseTabelViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
    }
    
    appraiseTutorModal *tmp = [_commentArray objectAtIndex:indexPath.row];
    cell.label1.text = tmp.content;
    cell.label2.text = tmp.serviceTitle;
    [cell.imageView1 sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    cell.label3.text = tmp.nickName;
    cell.label4.text = tmp.createTime;

    return cell;
}

//查看更多按钮
-(void)watchMoreAction:(UIButton *)button{
    
    tutorHomeCommentController *tutorCommentVC = [[tutorHomeCommentController alloc]init];
    tutorCommentVC.DetialID = self.toDetialID;
    tutorCommentVC.photoURLForComments = self.tutorPhoto;
    [self.navigationController pushViewController:tutorCommentVC animated:YES];
}

//更多详细信息
-(void)showMorebuttonAction:(UIButton *)button{
    showMoreViewController *showMoreVC = [[showMoreViewController alloc]init];
    showMoreVC.contentLabel = [[UILabel alloc]init];
    showMoreVC.titleLabel = [[UILabel alloc]init];
    showMoreVC.titleLabel.text = self.label14.text;
    showMoreVC.contentLabel.text = self.tutorM.serviceContent;

    [self.navigationController pushViewController:showMoreVC animated:YES];
}

//收藏
-(void)collectionAction:(UIButton *)button{
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {

        NSData *data = [AFNConnect createDataForisLikeTutor:[[[NSUserDefaults standardUserDefaults]objectForKey:@"userInfo"] objectForKey:@"uid"] andTutorID:self.toDetialID];
        [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:data connectBlock:^(id data) {
            
            NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
            
            NSString *state = [dic objectForKey:@"likeTeacher"];
            
            if ([state isEqualToString:@"true"]) {
                
                NSData *data = [AFNConnect createDataForCancelTutor:[[[NSUserDefaults standardUserDefaults]objectForKey:@"userInfo"] objectForKey:@"uid"] andTutorID:self.toDetialID];
                [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:data connectBlock:^(id data) {
                    
                    NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
                    
                    NSString *state = [dic objectForKey:@"state"];
                    
                    if ([state isEqualToString:@"success"]) {
                        
                        [self.button1 setBackgroundImage:[UIImage imageNamed:@"tutorHome_favor.png"] forState:UIControlStateNormal];
                    
                        
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"取消成功" delegate:self cancelButtonTitle:@"确认" otherButtonTitles:nil, nil];
                        [alert show];
                        
                    } else {
                        [self.button1 setBackgroundImage:[UIImage imageNamed:@"collection.png"] forState:UIControlStateNormal];
                        
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"取消失败" delegate:self cancelButtonTitle:@"确认" otherButtonTitles:nil, nil];
                        [alert show];
                        
                    }
                }];
                
            } else {

                NSData *data = [AFNConnect createDataForCollectTutor:[[[NSUserDefaults standardUserDefaults]objectForKey:@"userInfo"] objectForKey:@"uid"] andTutorID:self.toDetialID];
                [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:data connectBlock:^(id data) {
                    
                    NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
                    
                    NSString *state = [dic objectForKey:@"state"];
                    NSString *msg = [dic objectForKey:@"msg"];
                    
                    if ([msg isEqualToString:@"uid is not existed"]) {
                        
                        [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                        if ([TagForClient shareTagDataHandle].isAlert) {
                            
                        } else {
                            [TagForClient shareTagDataHandle].isAlert = YES;
                            UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"提示" message:@"您的登录失效，请重新登录" delegate:self cancelButtonTitle:@"确认" otherButtonTitles:nil, nil];
                            [alert show];
                        }
                    }
                    
                    if ([state isEqualToString:@"success"]) {
                        
                        [self.button1 setBackgroundImage:[UIImage imageNamed:@"collection.png"] forState:UIControlStateNormal];
                        
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"收藏成功" delegate:self cancelButtonTitle:@"确认" otherButtonTitles:nil, nil];
                        [self.view addSubview:alert];
                        [alert show];
                        
                    }else{
                        
                        [self.button1 setBackgroundImage:[UIImage imageNamed:@"tutorHome_favor.png"] forState:UIControlStateNormal];
                        
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"收藏失败" delegate:self cancelButtonTitle:@"确认" otherButtonTitles:nil, nil];
                        [self.view addSubview:alert];
                        [alert show];
                        
                    }
                }];
                
            
            }
        }];
    }else {
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您还未登录，请先登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
        [self.view addSubview:alert];
        [alert show];
        
        alert.delegate = self;
    }

}

#pragma mark  分享

-(void)buttonShareAction{

    [self showShareActionSheet:self.view];
}


- (void)showLoadingView:(BOOL)flag
{
    if (flag)
    {
        [self.view addSubview:self.panelView];
        [self.loadingView startAnimating];
    }
    else
    {
        [self.panelView removeFromSuperview];
    }
}



#pragma mark =========================================== 显示分享菜单

/**
 *  显示分享菜单
 *
 *  @param view 容器视图
 */
- (void)showShareActionSheet:(UIView *)view
{
    // 在简单分享中，只要设置共有分享参数即可分享到任意的社交平台
    __weak tutorHomeViewController *theController = self;
    
    //1、创建分享参数（必要）
    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
    
    NSArray* imageArray = @[self.imageView.image];
    
    //微博
    NSString *tutorConnect = [NSString stringWithFormat:@"【一英里导师】:%@(%@ %@) http://www.1yingli.cn/personal.html?tid=%@",self.nameLabel.text,self.tutorM.simpleShow1, self.tutorM.simpleShow2, self.toDetialID];
    
    NSString *titleForWechat = [NSString stringWithFormat:@"【一英里导师】%@(%@ %@)", self.tutorM.name, self.tutorM.simpleShow1, self.tutorM.simpleShow2];
    NSString *contentText = [_tutorM.introduce substringToIndex:140];
    
    //微信
    [shareParams SSDKSetupWeChatParamsByText:self.tutorM.introduce
                                       title:titleForWechat
                                         url:[NSURL URLWithString:[NSString stringWithFormat:@"http://www.1yingli.cn/personal.html?tid=%@", self.toDetialID]]
                                  thumbImage:self.tutorPhoto
                                       image:self.tutorPhoto
                                musicFileURL:nil
                                     extInfo:nil
                                    fileData:nil
                                emoticonData:nil
                                        type:SSDKContentTypeWebPage
                          forPlatformSubType:SSDKPlatformTypeWechat];
    
    //微信链接到的网站 导师主页
    [shareParams SSDKSetupShareParamsByText:contentText
                                     images:imageArray
                                        url:[NSURL URLWithString:[NSString stringWithFormat:@"http://www.1yingli.cn/personal.html?tid=%@", self.toDetialID]]
                                      title:titleForWechat
                                       type:SSDKContentTypeAuto];
    //微博
    
    [shareParams SSDKSetupSinaWeiboShareParamsByText:tutorConnect
                                               title:tutorConnect
                                               image:imageArray
                                                 url:[NSURL URLWithString:[NSString stringWithFormat:@"http://www.1yingli.cn/personal.html?tid=%@", self.toDetialID]]
                                            latitude:0
                                           longitude:0
                                            objectID:nil
                                                type:SSDKContentTypeAuto];
    
    //1.2、自定义分享平台（非必要）
    NSMutableArray *activePlatforms = [NSMutableArray arrayWithArray:[ShareSDK activePlatforms]];
    
    //2、分享
    [ShareSDK showShareActionSheet:view
                             items:activePlatforms
                       shareParams:shareParams
               onShareStateChanged:^(SSDKResponseState state, SSDKPlatformType platformType, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error, BOOL end) {
                   
            switch (state) {
                
                case SSDKResponseStateBegin:
                {
                    [theController showLoadingView:YES];
                    break;
                }
                case SSDKResponseStateSuccess:
                {
                    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"分享成功"
                                                                        message:nil
                                                                        delegate:nil
                                                                cancelButtonTitle:nil
                                                                otherButtonTitles:nil];
                    [NSTimer scheduledTimerWithTimeInterval:0.8f
                                                     target:self
                                                   selector:@selector(dispearViewMethod:)
                                                   userInfo:alertView
                                                    repeats:YES];
                    [alertView show];
                    break;
                }
                case SSDKResponseStateFail:
                {
                    if (platformType == SSDKPlatformTypeSMS && [error code] == 201)
                    {
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享失败"
                                                                        message:@"失败原因可能是：1、短信应用没有设置帐号；2、设备不支持短信应用；3、短信应用在iOS 7以上才能发送带附件的短信。"
                                                                        delegate:nil
                                                                cancelButtonTitle:@"OK"
                                                                otherButtonTitles:nil, nil];
                        [alert show];
                        break;
                    }
                    else if(platformType == SSDKPlatformTypeMail && [error code] == 201)
                    {
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享失败"
                                                                        message:@"失败原因可能是：1、邮件应用没有设置帐号；2、设备不支持邮件应用；"
                                                                        delegate:nil
                                                                cancelButtonTitle:@"OK"
                                                                otherButtonTitles:nil, nil];
                        [alert show];
                        break;
                    }
                    else
                    {
                        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享失败"
                                                                        message:[NSString stringWithFormat:@"%@",error]
                                                                        delegate:nil
                                                                cancelButtonTitle:@"OK"
                                                                otherButtonTitles:nil, nil];
                        NSLog(@"%@",error);
                        [alert show];
                        break;
                    }
                    break;
                }
                case SSDKResponseStateCancel:
                {
                    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"分享已取消"
                                                                        message:nil
                                                                        delegate:nil
                                                                cancelButtonTitle:nil
                                                                otherButtonTitles:nil];
                    [NSTimer scheduledTimerWithTimeInterval:1.0f
                                                     target:self
                                                   selector:@selector(dispearViewMethod:)
                                                   userInfo:alertView
                                                    repeats:YES];
                    [alertView show];
                    break;
                }
                default:
                    break;
            }
            
            if (state != SSDKResponseStateBegin)
            {
                [theController showLoadingView:NO];
            }
        }];
}

-(void)dispearViewMethod:(NSTimer *)theTimer
{
    UIAlertView *promptAlert = (UIAlertView *)[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
}

#pragma mark -- alertViewDelegate  提示框登录的方法

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 1) {
        
        LoginViewController *loginVC = [[LoginViewController alloc] init];
        loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
        
        [self presentViewController:loginVC animated:YES completion:^{
            
    }];
    }
}

//自适应高度
-(void)frameSize{
    
    self.label17.frame = CGRectMake(self.label16.frame.origin.x,self.label16.frame.origin.y + self.label16.frame.size.height + 10,self.view.frame.size.width - self.label16.frame.origin.x * 2,[tutorHomeViewController heightForcell:self.label17.text]);
    
    self.label17.text = [self.label17.text stringByReplacingOccurrencesOfString:@"</h1>" withString:@""];
    self.label17.text = [self.label17.text stringByReplacingOccurrencesOfString:@"<br>" withString:@""];
    
    self.label18.frame = CGRectMake(self.imageView5.frame.origin.x,self.label17.frame.origin.y + _label17.frame.size.height + 10,100,25);
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
