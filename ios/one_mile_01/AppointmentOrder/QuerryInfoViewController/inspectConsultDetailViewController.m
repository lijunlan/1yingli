//
//  inspectConsultDetailViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "inspectConsultDetailViewController.h"

@interface inspectConsultDetailViewController ()

@end

@implementation inspectConsultDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor colorWithRed:240/255.0 green:240/255.0 blue:240/255.0 alpha:1.0];
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 25,40,50, 30)];
    titleLabel.text = @"详情";
    titleLabel.font = [UIFont systemFontOfSize:23];
    titleLabel.textColor = [UIColor blackColor];
    [self.view addSubview:titleLabel];
    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, titleLabel.frame.origin.y, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backbutton:) forControlEvents:UIControlEventTouchDown];
    [self.view addSubview:button];

    
    //    预约话题
    UILabel *appointment = [[UILabel alloc]initWithFrame:CGRectMake(button.frame.origin.x ,button.frame.origin.y + button.frame.size.height + 10,200,20)];
    appointment.text = @"预约话题";
    appointment.backgroundColor = [UIColor clearColor];
    appointment.textColor = [UIColor colorWithRed:106/255.0 green:107/255.0 blue:108/255.0 alpha:1.0];
    appointment.font = [UIFont systemFontOfSize:12];
    [self.view addSubview:appointment];
    
//    蓝色背景
    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(appointment.frame.origin.x,appointment.frame.origin.y + appointment.frame.size.height + 5,self.view.frame.size.width - appointment.frame.origin.x * 2,40)];
    view.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    view.layer.masksToBounds = YES;
    view.layer.cornerRadius = 20;
    [self.view addSubview:view];

    //预约标题
     self.appointmentTitle = [[UILabel alloc]initWithFrame:CGRectMake(appointment.frame.origin.x + 5,appointment.frame.origin.y + appointment.frame.size.height + 5,200,40)];
    _appointmentTitle.text = self.orderForInspect.title;
    _appointmentTitle.font = [UIFont systemFontOfSize:16];
    _appointmentTitle.textColor = [UIColor whiteColor];
    [self.view addSubview:_appointmentTitle];

    //    200元/次
    self.moneyLabel = [[UILabel alloc]init];
    [view addSubview:self.moneyLabel];
    [self.moneyLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(view.mas_bottom).offset(-(view.frame.size.height - 3 - 15));
        make.left.equalTo(view.mas_left).offset(view.frame.size.width - 5 - 70);
        make.right.equalTo(view.mas_right).offset(-5);
        make.top.equalTo(view.mas_top).offset(3);
    }];
    _moneyLabel.text = [NSString stringWithFormat:@"%@次/元", self.orderForInspect.price];
    _moneyLabel.font = [UIFont systemFontOfSize:13];
    _moneyLabel.textColor = [UIColor whiteColor];
    
    //    约2小时
    self.hourLabel = [[UILabel alloc]init];
    [view addSubview:self.hourLabel];
    [self.hourLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(view.mas_bottom).offset(-3);
        make.left.equalTo(view.mas_left).offset(view.frame.size.width - 5 - 70);
        make.right.equalTo(view.mas_right).offset(-5);
        make.top.equalTo(view.mas_top).offset(view.frame.size.height - 6 - 15);
    }];
    _hourLabel.text = [NSString stringWithFormat:@" 约%@小时", [self.orderForInspect.time substringToIndex:1]];
    _hourLabel.font = [UIFont systemFontOfSize:11];
    _hourLabel.textColor = [UIColor whiteColor];
    
    //    滚动
    self.scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(appointment.frame.origin.x, view.frame.origin.y + view.frame.size.height + 10 , self.view.frame.size.width - appointment.frame.origin.x * 2 , self.view.frame.size.height - view.frame.origin.y - view.frame.size.height - 10 - 59)];
    self.scrollView.pagingEnabled = YES;
    self.scrollView.backgroundColor = [UIColor clearColor];
    //self.scrollView.backgroundColor = [UIColor yellowColor];
    self.scrollView.contentSize = CGSizeMake(self.view.frame.size.width * 3, 0);
    self.scrollView.delegate = self;
    [self.view addSubview:self.scrollView];
  
    //   问题
    UIView *questionView = [[UIView alloc]initWithFrame:CGRectMake(0, 0,self.view.frame.size.width - appointment.frame.origin.x * 2  , self.view.frame.size.height - view.frame.origin.y - view.frame.size.height - 10 - 59)];
    questionView.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:questionView];
    
    UILabel *questionTitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 20, questionView.frame.size.width - 20, 30)];
    questionTitleLabel.text = @"学员提出的问题";
    questionTitleLabel.textAlignment = NSTextAlignmentCenter;
    [questionView addSubview:questionTitleLabel];
    
    UILabel *oneline = [[UILabel alloc]initWithFrame:CGRectMake(15, questionTitleLabel.frame.origin.y + questionTitleLabel.frame.size.height + 5, questionView.frame.size.width - 30, 0.5)];
    oneline.backgroundColor = [UIColor lightGrayColor];
    [questionView addSubview:oneline];
    
    
    UILabel *questionContentLabel = [[UILabel alloc]initWithFrame:CGRectMake(oneline.frame.origin.x, oneline.frame.origin.y + 5, oneline.frame.size.width, 370)];
    questionContentLabel.textColor = [UIColor lightGrayColor];
    questionContentLabel.font = [UIFont systemFontOfSize:15];
    //questionContentLabel.backgroundColor = [UIColor redColor];
    questionContentLabel.text = self.orderForInspect.question;
    questionContentLabel.numberOfLines= 0;
    [questionView addSubview:questionContentLabel];
    
    //   自我介绍
    UIView *introduceView = [[UIView alloc] initWithFrame:CGRectMake(questionView.frame.origin.x + questionView.frame.size.width, questionView.frame.origin.y, questionView.frame.size.width, questionView.frame.size.height)];
    introduceView.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:introduceView];
    
    UILabel *introduceTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 20, introduceView.frame.size.width - 20, 30)];
    introduceTitleLabel.text = @"学员自我介绍";
    introduceTitleLabel.textAlignment = NSTextAlignmentCenter;
    [introduceView addSubview:introduceTitleLabel];
    
    UILabel *twoline = [[UILabel alloc] initWithFrame:CGRectMake(15, introduceTitleLabel.frame.origin.y + introduceTitleLabel.frame.size.height + 5, introduceView.frame.size.width - 30, 0.5)];
    twoline.backgroundColor = [UIColor lightGrayColor];
    [introduceView addSubview:twoline];
    
    UILabel *introduceContentLabel = [[UILabel alloc] initWithFrame:CGRectMake(twoline.frame.origin.x, twoline.frame.origin.y + 5, twoline.frame.size.width, 370)];
    introduceContentLabel.textColor = [UIColor lightGrayColor];
    introduceContentLabel.font = [UIFont systemFontOfSize:15.0f];
    introduceContentLabel.text = self.orderForInspect.userIntroduce;
    introduceContentLabel.numberOfLines = 0;
    [introduceView addSubview:introduceContentLabel];
    
    //   请教的问题
    UIView *timeView = [[UIView alloc]initWithFrame:CGRectMake(introduceView.frame.origin.x + introduceView.frame.size.width, introduceView.frame.origin.y, questionView.frame.size.width, introduceView.frame.size.height / 2 - 10)];
    timeView.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:timeView];
    
    UILabel *appointTime = [[UILabel alloc]initWithFrame:CGRectMake(10, 10, timeView.frame.size.width - 20, 30)];
    appointTime.text = @"学员方便时间";
    appointTime.textAlignment = NSTextAlignmentCenter;
    [timeView addSubview:appointTime];
    
    UILabel *threeline = [[UILabel alloc]initWithFrame:CGRectMake(15, appointTime.frame.origin.y + appointTime.frame.size.height + 5, timeView.frame.size.width - 30, 0.5)];
    threeline.backgroundColor = [UIColor lightGrayColor];
    [timeView addSubview:threeline];
    
    UILabel *contentLabel = [[UILabel alloc]initWithFrame:CGRectMake(threeline.frame.origin.x, threeline.frame.origin.y + 5, threeline.frame.size.width, timeView.frame.size.height - threeline.frame.size.height - 60)];
    contentLabel.textColor = [UIColor lightGrayColor];
    contentLabel.font = [UIFont systemFontOfSize:15];
    contentLabel.text = self.orderForInspect.question;
//    contentLabel.backgroundColor = [UIColor redColor];
    contentLabel.numberOfLines= 0;
    [timeView addSubview:contentLabel];
    
    
    
    //   联系方式
    UIView *contactView = [[UIView alloc]initWithFrame:CGRectMake(timeView.frame.origin.x, timeView.frame.origin.y + timeView.frame.size.height + 20, questionView.frame.size.width, introduceView.frame.size.height / 2 - 10)];
    contactView.backgroundColor = [UIColor whiteColor];
    [self.scrollView addSubview:contactView];
    
    UILabel *contactTitle = [[UILabel alloc]initWithFrame:CGRectMake(10, 10, contactView.frame.size.width - 20, 30)];
    contactTitle.text = @"学员联系方式";
    contactTitle.textAlignment = NSTextAlignmentCenter;
    [contactView addSubview:contactTitle];
    
    UILabel *fourline = [[UILabel alloc]initWithFrame:CGRectMake(15, contactTitle.frame.origin.y + contactTitle.frame.size.height + 5, contactView.frame.size.width - 30, 0.5)];
    fourline.backgroundColor = [UIColor lightGrayColor];
    [contactView addSubview:fourline];
    

    UILabel *phoneLabel = [[UILabel alloc]initWithFrame:CGRectMake(30, fourline.frame.origin.y + 10, 60, 25)];
    //phoneLabel.backgroundColor = [UIColor yellowColor];
    phoneLabel.text = @"手机:";
    [contactView addSubview:phoneLabel];
    
    self.phoneNum= [[UILabel alloc]initWithFrame:CGRectMake(phoneLabel.frame.origin.x + phoneLabel.frame.size.width + 10, phoneLabel.frame.origin.y, contactView.frame.size.width - phoneLabel.frame.size.width - phoneLabel.frame.origin.x - 20  , 25)];
    //self.phoneNum.backgroundColor = [UIColor redColor];
    _phoneNum.text = self.orderForInspect.phone;
    [contactView addSubview:self.phoneNum];
    
    

    UILabel *weixinLabel = [[UILabel alloc]initWithFrame:CGRectMake(30, phoneLabel.frame.origin.y + phoneLabel.frame.size.height + 10, 60, 25)];
    //weixinLabel.backgroundColor = [UIColor yellowColor];
    weixinLabel.text = @"微信:";
    [contactView addSubview:weixinLabel];
    self.weixinNum= [[UILabel alloc]initWithFrame:CGRectMake(weixinLabel.frame.origin.x + weixinLabel.frame.size.width + 10, weixinLabel.frame.origin.y,  contactView.frame.size.width - phoneLabel.frame.size.width - phoneLabel.frame.origin.x - 20, 25)];
    //self.weixinNum.backgroundColor = [UIColor redColor];
    [contactView addSubview:self.weixinNum];

    

    UILabel *emailLabel = [[UILabel alloc]initWithFrame:CGRectMake(30, weixinLabel.frame.origin.y + weixinLabel.frame.size.height + 10, 60, 25)];
    //emailLabel.backgroundColor = [UIColor yellowColor];
    emailLabel.text = @"邮箱:";
    [contactView addSubview:emailLabel];
    
    self.emailNum= [[UILabel alloc]initWithFrame:CGRectMake(emailLabel.frame.origin.x + emailLabel.frame.size.width + 10, emailLabel.frame.origin.y,  contactView.frame.size.width - phoneLabel.frame.size.width - phoneLabel.frame.origin.x - 20, 25)];
    //self.emailNum.backgroundColor = [UIColor redColor];
    self.emailNum.text = self.orderForInspect.email;
    [contactView addSubview:self.emailNum];
    
    
    // Do any additional setup after loading the view.
}


-(void)backbutton:(UIButton *)button{

    [self.navigationController popToRootViewControllerAnimated:YES];
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
