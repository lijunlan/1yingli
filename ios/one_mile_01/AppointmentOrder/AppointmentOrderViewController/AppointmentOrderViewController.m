//
//  AppointmentOrderViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/26.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "AppointmentOrderViewController.h"
#import "UserInfoModel.h"
#import "Order.h"
#import "DataSigner.h"
#import "RSADataSigner.h"
#import <AlipaySDK/AlipaySDK.h>
#import "QueryProduct.h"

@interface AppointmentOrderViewController ()<UIAlertViewDelegate>

@property (nonatomic, strong) QuerryInfoView *querryInfoV;
@property (nonatomic, strong) UITextView *textView;
@property (nonatomic, strong) UITextView *textView2;
@property (nonatomic, strong) UITextField *textFiled;
@property (nonatomic, strong) UITextView *timeContent;

@property (nonatomic, strong) UserInfoModel *queryInfo;
@property (nonatomic, strong) QueryProduct *product;

@end

@implementation AppointmentOrderViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.queryInfo = [[UserInfoModel alloc] init];
        self.product = [[QueryProduct alloc] init];
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor colorWithRed:240/255.0 green:240/255.0 blue:240/255.0 alpha:1.0];
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 25,40,50, 30)];
    titleLabel.text = @"预约";
    titleLabel.font = [UIFont systemFontOfSize:23];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, titleLabel.frame.origin.y, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backbutton:) forControlEvents:UIControlEventTouchDown];
    [self.view addSubview:button];
    
//    预约话题
    UILabel *label1 = [[UILabel alloc]initWithFrame:CGRectMake(button.frame.origin.x ,button.frame.origin.y + button.frame.size.height + 10,200,20)];
    label1.text = @"预约话题";
    label1.backgroundColor = [UIColor clearColor];
    label1.textColor = [UIColor colorWithRed:106/255.0 green:107/255.0 blue:108/255.0 alpha:1.0];
    label1.font = [UIFont systemFontOfSize:12];
    [self.view addSubview:label1];
    
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(label1.frame.origin.x,label1.frame.origin.y + label1.frame.size.height + 5,self.view.frame.size.width - label1.frame.origin.x * 2,40)];
    view.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    view.layer.masksToBounds = YES;
    view.layer.cornerRadius = 20;
    [self.view addSubview:view];
    
    
    //预约标题
    UILabel *label2 = [[UILabel alloc]initWithFrame:CGRectMake(label1.frame.origin.x,label1.frame.origin.y + label1.frame.size.height + 5,view.frame.size.width - 85,40)];
    label2.text = [NSString stringWithFormat:@" %@", self.appointTopic];
    label2.font = [UIFont systemFontOfSize:15];
    label2.textColor = [UIColor whiteColor];
    label2.layer.masksToBounds = YES;
    label2.layer.cornerRadius = 20;
    [self.view addSubview:label2];
    

    //    200元/次
    self.label3 = [[UILabel alloc]initWithFrame:CGRectMake(view.frame.size.width - 80, 3,80,15)];
    _label3.text = self.pricePertimes;
    _label3.font = [UIFont systemFontOfSize:13];
    _label3.textColor = [UIColor whiteColor];
    [view addSubview:_label3];
    
    //    约2小时
    UILabel *label4 = [[UILabel alloc]initWithFrame:CGRectMake(view.frame.size.width - 80,_label3.frame.origin.y + _label3.frame.size.height ,80,20)];
    label4.text = @"  约2小时";
    label4.font = [UIFont systemFontOfSize:11];
    label4.textColor = [UIColor whiteColor];
    [view addSubview:label4];
    
//    滚动
    self.scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(label2.frame.origin.x, label2.frame.origin.y + label2.frame.size.height + 10 , self.view.frame.size.width - label2.frame.origin.x , 350)];
    
    if (iPhone5) {
        self.scrollView.frame = CGRectMake(label2.frame.origin.x, label2.frame.origin.y + label2.frame.size.height + 10 , self.view.frame.size.width - label2.frame.origin.x , 250);
        self.scrollView.contentSize = CGSizeMake(270 * 3 + 30, 0);

    }else if(iPhone6){
        self.scrollView.frame = CGRectMake(label2.frame.origin.x, label2.frame.origin.y + label2.frame.size.height + 10 , self.view.frame.size.width - label2.frame.origin.x , 350);
        self.scrollView.contentSize = CGSizeMake(290 * 3 + 70, 0);
    }else if(iPhone6P){
        
        self.scrollView.frame = CGRectMake(label2.frame.origin.x, label2.frame.origin.y + label2.frame.size.height + 10 , self.view.frame.size.width - label2.frame.origin.x , 420);
        self.scrollView.contentSize = CGSizeMake(350 * 3 + 40, 0);

    }
    
    self.scrollView.pagingEnabled = YES;
    self.scrollView.backgroundColor = [UIColor clearColor];
    self.scrollView.delegate = self;
    [self.view addSubview:self.scrollView];
    
//   请教的问题
    UIImageView *imageView1 = [[UIImageView alloc]init];
    if (iPhone5) {
        imageView1.frame = CGRectMake(0,0, 270, self.scrollView.frame.size.height);
    }else if(iPhone6){
    imageView1.frame = CGRectMake(0,0, 290, self.scrollView.frame.size.height);
    }else if (iPhone6P){
    imageView1.frame = CGRectMake(0,0, 350, self.scrollView.frame.size.height);
    }
    imageView1.layer.cornerRadius = 10;
    imageView1.image = [UIImage imageNamed:@"appointment_bg.png"];
    imageView1.userInteractionEnabled = YES;
    [self.scrollView addSubview:imageView1];
    
    //告诉我们你要请教的问题?
    UILabel *label5 = [[UILabel alloc]initWithFrame:CGRectMake(10,10,200,20)];
    label5.text = @"告诉我们你要请教的问题?";
    label5.font = [UIFont systemFontOfSize:16];
    label5.textColor = [UIColor blackColor];
    [imageView1 addSubview:label5];
    //    30字 - 300字之间
    UILabel *label6 = [[UILabel alloc]initWithFrame:CGRectMake(label5.frame.origin.x,label5.frame.origin.y + label5.frame.size.height,label5.frame.size.width,label5.frame.size.height)];
    label6.text = @"(30字 - 300字之间)";
    label6.font = [UIFont systemFontOfSize:12];
    label6.textColor = [UIColor blackColor];
    [imageView1 addSubview:label6];

    //    白线
    UILabel *label7 = [[UILabel alloc]initWithFrame:CGRectMake(label6.frame.origin.x,label6.frame.origin.y + label6.frame.size.height,imageView1.frame.size.width - label6.frame.origin.x * 2,label6.frame.size.height)];
    label7.textColor = [UIColor colorWithRed:240/255.0 green:240/255.0 blue:240/255.0 alpha:1.0];
    [imageView1 addSubview:label7];

//    输入框中的内容
    self.textView = [[UITextView alloc]initWithFrame:CGRectMake(label7.frame.origin.x + 5, label7.frame.origin.y + 5, label7.frame.size.width - 10, imageView1.frame.size.height - label7.frame.origin.y - 25)];
//    textFile.backgroundColor = [UIColor yellowColor];
    _textView.layer.borderWidth = 1;
    _textView.layer.borderColor = [UIColor colorWithRed:214 / 255.0 green:214 / 255.0 blue:214 / 255.0 alpha:1.0].CGColor;
    _textView.textColor = [UIColor blackColor];
    _textView.delegate = self;
    [imageView1 addSubview:_textView];
  
    //   介绍自己
    UIImageView *imageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(imageView1.frame.origin.x + imageView1.frame.size.width + 5,0, imageView1.frame.size.width,imageView1.frame.size.height)];
    imageView2.layer.cornerRadius = 10;
    imageView2.image = [UIImage imageNamed:@"appointment_bg.png"];
    imageView2.layer.cornerRadius = 10;
    imageView2.userInteractionEnabled = YES;
    [self.scrollView addSubview:imageView2];
    
    //介绍自己?
    UILabel *label8 = [[UILabel alloc]initWithFrame:CGRectMake(10,10,200,20)];
    label8.text = @"介绍自己?";
    label8.font = [UIFont systemFontOfSize:16];
    label8.textColor = [UIColor blackColor];
    [imageView2 addSubview:label8];
    //    30字 - 300字之间
    UILabel *label9 = [[UILabel alloc]initWithFrame:CGRectMake(label8.frame.origin.x,label8.frame.origin.y + label8.frame.size.height,label8.frame.size.width,label8.frame.size.height)];
    label9.text = @"(30字 - 300字之间)";
    label9.font = [UIFont systemFontOfSize:12];
    label9.textColor = [UIColor blackColor];
    [imageView2 addSubview:label9];
    
    //    白线
    UILabel *label10 = [[UILabel alloc]initWithFrame:CGRectMake(label9.frame.origin.x,label9.frame.origin.y + label9.frame.size.height,imageView2.frame.size.width - label9.frame.origin.x * 2,label9.frame.size.height)];
    label10.textColor = [UIColor colorWithRed:240/255.0 green:240/255.0 blue:240/255.0 alpha:1.0];
    [imageView2 addSubview:label10];
    
    //    输入框中的内容
    self.textView2 = [[UITextView alloc]initWithFrame:CGRectMake(label10.frame.origin.x + 5, label10.frame.origin.y + 5, label10.frame.size.width - 10, imageView2.frame.size.height - label10.frame.origin.y - 25)];
//    textFile2.backgroundColor = [UIColor yellowColor];
    _textView2.textColor = [UIColor blackColor];
    _textView2.font = [UIFont systemFontOfSize:13];
    _textView2.layer.borderWidth = 1;
    _textView2.layer.borderColor = [UIColor colorWithRed:214 / 255.0 green:214 / 255.0 blue:214 / 255.0 alpha:1.0].CGColor;
    _textView2.delegate = self;
    [imageView2 addSubview:_textView2];
    
    //   预约时间表
    self.imageView3 = [[UIImageView alloc]initWithFrame:CGRectMake(imageView2.frame.origin.x + imageView2.frame.size.width + 5,45, self.view.frame.size.width - self.scrollView.frame.origin.x * 2 ,imageView2.frame.size.height - 45)];
    self.imageView3.layer.cornerRadius = 10;
    self.imageView3.image = [UIImage imageNamed:@"appointment_bg.png"];
    self.imageView3.layer.cornerRadius = 10;
    self.imageView3.userInteractionEnabled = YES;
    [self.scrollView addSubview:self.imageView3];
    
    //    导师预约时间表
    UILabel *label11 = [[UILabel alloc]initWithFrame:CGRectMake(self.imageView3.frame.origin.x + (self.imageView3.frame.size.width - 140) / 2,imageView1.frame.origin.y + 10,140,30)];
    label11.text = @"导师预约时间表";
    label11.font = [UIFont systemFontOfSize:18];
    label11.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.scrollView addSubview:label11];

    //    为可预约时间
    UILabel *label31 = [[UILabel alloc]initWithFrame:CGRectMake(30,20,65,25)];
    label31.text = @"预约时间";
    label31.font = [UIFont systemFontOfSize:15];
    label31.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.imageView3 addSubview:label31];

//   时区
    self.textFiled = [[UITextField alloc]initWithFrame:CGRectMake(label31.frame.origin.x + label31.frame.size.width ,label31.frame.origin.y,self.imageView3.frame.size.width - label31.frame.origin.x - label31.frame.size.width - 20,25)];
    _textFiled.backgroundColor = [UIColor clearColor];
    _textFiled.layer.borderWidth = 1;
    _textFiled.placeholder = @" 请输入你所在地的时区";
    _textFiled.clearButtonMode = UITextFieldViewModeAlways;
    _textFiled.font = [UIFont systemFontOfSize:15];
    _textFiled.layer.borderColor = [UIColor colorWithRed:214 / 255.0 green:214 / 255.0 blue:214 / 255.0 alpha:1.0].CGColor;
    [self.imageView3 addSubview:_textFiled];
    
//    文字描述
    self.timeContent = [[UITextView alloc]initWithFrame:CGRectMake(20,60,self.imageView3.frame.size.width - 40,self.imageView3.frame.size.height - self.textFiled.frame.origin.y - self.textFiled.frame.size.height - 30)];
    _timeContent.backgroundColor = [UIColor clearColor];
    _timeContent.layer.borderWidth = 1;
    _timeContent.layer.borderColor = [UIColor colorWithRed:214 / 255.0 green:214 / 255.0 blue:214 / 255.0 alpha:1.0].CGColor;
    _timeContent.delegate = self;
    [self.imageView3 addSubview:_timeContent];

//    小数字
    UILabel *label12 = [[UILabel alloc]initWithFrame:CGRectMake(self.scrollView.frame.origin.x + 10 , self.scrollView.frame.origin.y + self.scrollView.frame.size.height + 10,15, 15)];
    label12.layer.masksToBounds = YES;
    label12.layer.cornerRadius = 7;
    label12.text = @"1";
    label12.font = [UIFont systemFontOfSize:10];
    label12.textAlignment = NSTextAlignmentCenter;
    label12.textColor = [UIColor whiteColor];
    label12.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label12];
    //蓝线
    UILabel *label13 = [[UILabel alloc]initWithFrame:CGRectMake(label12.frame.origin.x + label12.frame.size.width, label12.frame.origin.y + 7 ,60, 1)];
    if (iPhone5) {
        label13.frame = CGRectMake(label12.frame.origin.x + label12.frame.size.width, label12.frame.origin.y + 7 ,45, 1);
    }else if (iPhone6P){
        label13.frame = CGRectMake(label12.frame.origin.x + label12.frame.size.width, label12.frame.origin.y + 7 ,70, 1);
    }
    label13.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label13];
    
    UILabel *label14 = [[UILabel alloc]initWithFrame:CGRectMake(label12.frame.origin.x -12, label12.frame.origin.y + label12.frame.size.height, 80, 20)];
    label14.backgroundColor = [UIColor clearColor];
    label14.text =@"学员申请";
    label14.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:label14];
        
//    导师确认
    //    小数字
    UILabel *label15 = [[UILabel alloc]initWithFrame:CGRectMake(label13.frame.origin.x + label13.frame.size.width ,label12.frame.origin.y,15, 15)];
    label15.layer.masksToBounds = YES;
    label15.layer.cornerRadius = 7;
    label15.text = @"2";
    label15.font = [UIFont systemFontOfSize:10];
    label15.textAlignment = NSTextAlignmentCenter;
    label15.textColor = [UIColor whiteColor];
    label15.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label15];
    //蓝线
    UILabel *label16 = [[UILabel alloc]initWithFrame:CGRectMake(label15.frame.origin.x + label15.frame.size.width, label15.frame.origin.y + 7 ,label13.frame.size.width, 1)];
    label16.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label16];
    
    UILabel *label17 = [[UILabel alloc]initWithFrame:CGRectMake(label15.frame.origin.x - 12, label15.frame.origin.y + label15.frame.size.height, 80, 20)];
    label17.backgroundColor = [UIColor clearColor];
    label17.text =@"导师确认";
    label17.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:label17];
    
    
    //    协商时间
    //    小数字
    UILabel *label18 = [[UILabel alloc]initWithFrame:CGRectMake(label16.frame.origin.x + label16.frame.size.width ,label12.frame.origin.y,15, 15)];
    label18.layer.masksToBounds = YES;
    label18.layer.cornerRadius = 7;
    label18.text = @"3";
    label18.font = [UIFont systemFontOfSize:10];
    label18.textAlignment = NSTextAlignmentCenter;
    label18.textColor = [UIColor whiteColor];
    label18.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label18];
    //蓝线
    UILabel *label19 = [[UILabel alloc]initWithFrame:CGRectMake(label18.frame.origin.x + label18.frame.size.width, label18.frame.origin.y + 7 ,label13.frame.size.width, 1)];
    label19.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label19];
    
    UILabel *label20 = [[UILabel alloc]initWithFrame:CGRectMake(label18.frame.origin.x -12, label18.frame.origin.y + label18.frame.size.height, 80, 20)];
    label20.backgroundColor = [UIColor clearColor];
    label20.text =@"协商时间";
    label20.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:label20];
    
    //    服务进行
    //    小数字
    UILabel *label21 = [[UILabel alloc]initWithFrame:CGRectMake(label19.frame.origin.x + label19.frame.size.width ,label12.frame.origin.y,15, 15)];
    label21.layer.masksToBounds = YES;
    label21.layer.cornerRadius = 7;
    label21.text = @"4";
    label21.font = [UIFont systemFontOfSize:10];
    label21.textAlignment = NSTextAlignmentCenter;
    label21.textColor = [UIColor whiteColor];
    label21.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label21];
    //蓝线
    UILabel *label22 = [[UILabel alloc]initWithFrame:CGRectMake(label21.frame.origin.x + label21.frame.size.width, label21.frame.origin.y + 7 ,label13.frame.size.width, 1)];
    label22.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label22];
    
    UILabel *label23 = [[UILabel alloc]initWithFrame:CGRectMake(label21.frame.origin.x -12, label21.frame.origin.y + label21.frame.size.height, 80, 20)];
    label23.backgroundColor = [UIColor clearColor];
    label23.text =@"服务进行";
    label23.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:label23];
    
    
//    双方评价
    //    小数字
    UILabel *label24 = [[UILabel alloc]initWithFrame:CGRectMake(label22.frame.origin.x + label22.frame.size.width ,label12.frame.origin.y,15, 15)];
    label24.layer.masksToBounds = YES;
    label24.layer.cornerRadius = 7;
    label24.text = @"5";
    label24.font = [UIFont systemFontOfSize:10];
    label24.textAlignment = NSTextAlignmentCenter;
    label24.textColor = [UIColor whiteColor];
    label24.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.view addSubview:label24];
    UILabel *label26 = [[UILabel alloc]initWithFrame:CGRectMake(label24.frame.origin.x -12, label24.frame.origin.y + label24.frame.size.height, 80, 20)];
    label26.backgroundColor = [UIColor clearColor];
    label26.text =@"双方评价";
    label26.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:label26];


    UILabel *label27 = [[UILabel alloc]initWithFrame:CGRectMake(label1.frame.origin.x,label26.frame.origin.y + label26.frame.size.height + 10,self.view.frame.size.width - label1.frame.origin.x * 2 ,35)];
    label27.backgroundColor = [UIColor clearColor];
    label27.layer.masksToBounds = YES;
    label27.layer.cornerRadius = 17;
    //边框宽度
    label27.layer.borderWidth = 1;
    //边框颜色
    label27.layer.borderColor = [UIColor colorWithRed:71 / 255.0 green:56 / 255.0 blue:57 / 255.0 alpha:0.6].CGColor;
    [self.view addSubview:label27];
    
//    警示图片
    UIImageView *imageView4 = [[UIImageView alloc]initWithFrame:CGRectMake(10, 4, 25, 25)];
    imageView4.image = [UIImage imageNamed:@"tutorAppointmentAttention"];
//    imageView4.backgroundColor = [UIColor redColor];
    [label27 addSubview:imageView4];
    
//    详细描述
    UILabel *label28 = [[UILabel alloc]initWithFrame:CGRectMake(imageView4.frame.origin.x +imageView4.frame.size.width + 10, imageView4.frame.origin.y, label27.frame.size.width - imageView4.frame.size.width - imageView4.frame.origin.x - 20, 13)];
    label28.text = @"详细描述有助于导师针对你的情况咨询做准备";
    label28.font = [UIFont systemFontOfSize:9];
    label28.textColor = [UIColor colorWithRed:172/255.0 green:172/255.0 blue:172/255.0 alpha:1.0];
    [label27 addSubview:label28];
    
    //    详细描述
    UILabel *label29 = [[UILabel alloc]initWithFrame:CGRectMake(label28.frame.origin.x, label28.frame.origin.y + label28.frame.size.height,label27.frame.size.width - imageView4.frame.size.width - imageView4.frame.origin.x - 20, 15)];
    label29.text = @"您填写的一英里资料只有一英里可以看到,不会泄露给其他人";
    label29.font = [UIFont systemFontOfSize:8];
    label29.textColor = [UIColor colorWithRed:228/255.0 green:172/255.0 blue:190/255.0 alpha:1.0];
    [label27 addSubview:label29];
    
//    提交
    UIButton *button2 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.origin.x,self.view.frame.size.height - 60,self.view.frame.size.width,60)];
    button2.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    [button2 setTitle:@"提交" forState:UIControlStateNormal];
    [button2 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [button2 addTarget:self action:@selector(submitAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button2];
    
    
    self.querryInfoV = [[QuerryInfoView alloc] initWithFrame:CGRectMake(0, HEIGHT, WIDTH, HEIGHT)];
    _querryInfoV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.9f];
    _querryInfoV.myDelegate = self;
    [self.view addSubview:_querryInfoV];
    
    
    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(backgroundTapped:)];
    [self.view addGestureRecognizer:resignKeyboard];

    // Do any additional setup after loading the view.
    [self getDetailUserInfo];
}


#pragma mark -- textFieldDelegate
-(void)textViewDidBeginEditing:(UITextView *)textView
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    self.view.frame = CGRectMake(0, -90, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}


-(void)textViewDidEndEditing:(UITextView *)textView
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.view.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}


//撤销键盘
-(void)backgroundTapped:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}


-(void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    if (scrollView.contentOffset.x > 300 && scrollView.contentOffset.x < 520) {
        if (iPhone6P) {
        [self.scrollView setContentOffset:CGPointMake(350, 0) animated:YES];
        }else{
        [self.scrollView setContentOffset:CGPointMake(290, 0) animated:YES];
        }
    }else if (scrollView.contentOffset.x > 270 && scrollView.contentOffset.x < 540){
    
        [self.scrollView setContentOffset:CGPointMake(270, 0) animated:YES];
    }
}


-(void)submitAction:(UIButton *)button
{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.querryInfoV.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
    }];
}

#pragma mark -- 取消和付款
-(void)cancelToBack
{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.querryInfoV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
        
    }];
}

-(void)querryToPay:(NSString *)clientName withPhone:(NSString *)phone withChat:(NSString *)chat withEmail:(NSString *)email
{
    if (clientName.length != 0 || ![[clientName substringToIndex:1] isEqualToString:@" "]) {
        _queryInfo.name = clientName;
    }
    
    if (phone.length != 0 || ![[phone substringToIndex:1] isEqualToString:@" "]) {
        _queryInfo.phone = phone;
    }
    
    if (email.length != 0 || ![[email substringToIndex:1] isEqualToString:@" "]) {
        _queryInfo.email = email;
    }
    
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.querryInfoV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
        [self getCreateOrder];
    } completion:^(BOOL finished) {
        
    }];
}

-(void)backbutton:(UIButton *)button{

    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark -- 获取数据
//获取个人自我介绍
-(void)getIntroduction
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForIntro:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"]] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            if (((NSString *)[dic objectForKey:@"introduce"]).length == 0) {
                
            } else {
            
                _textView2.text = [dic objectForKey:@"introduce"];
            }
        }
    }];
}

//获取个人详细信息
-(void)getDetailUserInfo
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForPersonalInfo:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"]] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        self.queryInfo.resume = [dic objectForKey:@"resume"];
        self.queryInfo.phone = [dic objectForKey:@"phone"];
        self.queryInfo.email = [dic objectForKey:@"email"];
        self.queryInfo.nickName = [dic objectForKey:@"nickName"];
        self.queryInfo.address = [dic objectForKey:@"address"];
        self.queryInfo.name = [dic objectForKey:@"name"];
        
        self.querryInfoV.userQueryM = self.queryInfo;
    }];
}

#pragma mark  =======
//创建订单
-(void)getCreateOrder
{
    if (_textView.text.length == 0 || _textView2.text.length == 0 || _timeContent.text.length == 0) {
        
        UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"不能为空，请认真填写" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [alertV show];
    } else {
        [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForCreateOrder:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withQuestion:_textView.text withIntro:_textView2.text withTeacherID:self.teacherIDForOrder withSelectTime:self.timeContent.text withName:_queryInfo.name withPhone:_queryInfo.phone withEmail:_queryInfo.email withContact:@"微信"] connectBlock:^(id data) {
            
            NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
            
            if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
                
                QuerryInfoViewController *querryInfoVC = [[QuerryInfoViewController alloc] init];
                querryInfoVC.contactWayForQuerry = _contactWay;
                [self.navigationController pushViewController:querryInfoVC animated:YES];
            } else {
                
                if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                    
                    [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                    
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
                    [alert show];
                } else {
                
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"预约失败" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                    [alert show];
                }
            }
        }];
    }
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
        
        if (buttonIndex == 1) {
            
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            
            loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
            
            [self presentViewController:loginVC animated:YES completion:^{
                
            }];
        }
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
