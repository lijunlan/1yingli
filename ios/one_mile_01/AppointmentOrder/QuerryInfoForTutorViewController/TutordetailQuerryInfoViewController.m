//
//  TutordetailQuerryInfoViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "TutordetailQuerryInfoViewController.h"
#import "inspectConsultDetailViewController.h"
@interface TutordetailQuerryInfoViewController ()<UIAlertViewDelegate>
@property(nonatomic,strong) UILabel *tutorContentTitleLabel;
@property(nonatomic,strong) UILabel *tutorContentLabel;

@property(nonatomic,strong)UILabel *oneLabel;
@property(nonatomic,strong)UILabel *oneLabelLine;
@property(nonatomic,strong)UILabel *applyLabel;

@property(nonatomic,strong) UILabel *twolabel;
@property(nonatomic,strong) UILabel *twoLabelLine;
@property(nonatomic,strong) UILabel *confirmLabel;

@property(nonatomic,strong) UILabel *threeLabel;
@property(nonatomic,strong) UILabel *threeLabelLine;
@property(nonatomic,strong) UILabel *consultLabel;

@property(nonatomic,strong) UILabel *fourlabel;
@property(nonatomic,strong) UILabel *fourLabelLine;
@property(nonatomic,strong) UILabel *severLabel;

@property(nonatomic,strong) UILabel *fiveLabel;
@property(nonatomic,strong) UILabel *judgeLabel;

@property(nonatomic,strong) UIButton *tutorInspectConsultbutton;
@property(nonatomic,strong) UIButton *tutorQuerryCancelbutton;
@property(nonatomic,strong) UIButton *tutorConfirmButton;

@property(nonatomic,strong) UITextField *yearTextF;
@property(nonatomic,strong) UITextField *mouthTextF;
@property(nonatomic,strong) UITextField *dayTextF;
@property(nonatomic,strong) UITextField *hourTextF;
@property(nonatomic,strong) UITextField *minuteTextF;
@property(nonatomic,strong) UITextField *numberOfHour;

@end

@implementation TutordetailQuerryInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(tutorDetailQuerryInfoBackbutton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    //订单详情
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 50, button.frame.origin.y,100, 30)];
    titleLabel.text = @"订单详情";
    titleLabel.font = [UIFont systemFontOfSize:23];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];

    
//    撤销键盘
    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(backgroundTapped:)];
    [self.view addGestureRecognizer:resignKeyboard];

    self.tutorContentTitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, button.frame.origin.y + button.frame.size.height + 30, self.view.frame.size.width - 20, 30)];
    _tutorContentTitleLabel.font = [UIFont systemFontOfSize:18];
    _tutorContentTitleLabel.textAlignment = NSTextAlignmentCenter;
    _tutorContentTitleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:_tutorContentTitleLabel];
    
    self.tutorContentLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, _tutorContentTitleLabel.frame.origin.y + _tutorContentTitleLabel.frame.size.height + 10, self.view.frame.size.width - 20,_tutorContentTitleLabel.frame.size.height + 30)];
    _tutorContentLabel.numberOfLines = 2;
    _tutorContentLabel.font = [UIFont systemFontOfSize:15];
    _tutorContentLabel.textAlignment = NSTextAlignmentCenter;
    _tutorContentLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:_tutorContentLabel];
    
    //    小数字
    self.oneLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.origin.x + 30 ,_tutorContentLabel.frame.origin.y + _tutorContentLabel.frame.size.height + 40,15, 15)];
    self.oneLabel.layer.masksToBounds = YES;
    self.oneLabel.layer.cornerRadius = 7;
    self.oneLabel.text = @"1";
    self.oneLabel.font = [UIFont systemFontOfSize:10];
    self.oneLabel.textAlignment = NSTextAlignmentCenter;
    self.oneLabel.textColor = [UIColor whiteColor];
    self.oneLabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:self.oneLabel];
    
    //蓝线
    self.oneLabelLine = [[UILabel alloc]init];
    if (iPhone6P) {
        self.oneLabelLine.frame =  CGRectMake(_oneLabel.frame.origin.x + _oneLabel.frame.size.width, _oneLabel.frame.origin.y + 7 ,70, 1);
    }else if (iPhone5){
        self.oneLabelLine.frame =  CGRectMake(_oneLabel.frame.origin.x + _oneLabel.frame.size.width, _oneLabel.frame.origin.y + 7 ,45, 1);
    }else if (iPhone6){
        self.oneLabelLine.frame =  CGRectMake(_oneLabel.frame.origin.x + _oneLabel.frame.size.width, _oneLabel.frame.origin.y + 7 ,60, 1);
    }
    
    _oneLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_oneLabelLine];
    
    self.applyLabel = [[UILabel alloc]initWithFrame:CGRectMake(_oneLabel.frame.origin.x -12, _oneLabel.frame.origin.y + _oneLabel.frame.size.height, 80, 20)];
    _applyLabel.backgroundColor = [UIColor clearColor];
    _applyLabel.text =@"学员申请";
    _applyLabel.textColor = [UIColor lightGrayColor];
    _applyLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_applyLabel];
    
    
    //    导师确认
    //    小数字
    self.twolabel = [[UILabel alloc]initWithFrame:CGRectMake(_oneLabelLine.frame.origin.x + _oneLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _twolabel.layer.masksToBounds = YES;
    _twolabel.layer.cornerRadius = 7;
    _twolabel.text = @"2";
    _twolabel.font = [UIFont systemFontOfSize:10];
    _twolabel.textAlignment = NSTextAlignmentCenter;
    _twolabel.textColor = [UIColor whiteColor];
    _twolabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_twolabel];
    
    //蓝线
    _twoLabelLine = [[UILabel alloc]initWithFrame:CGRectMake(_twolabel.frame.origin.x + _twolabel.frame.size.width, _twolabel.frame.origin.y + 7 ,_oneLabelLine.frame.size.width, 1)];
    _twoLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_twoLabelLine];
    
    
    self.confirmLabel = [[UILabel alloc]initWithFrame:CGRectMake(_twolabel.frame.origin.x - 12, _twolabel.frame.origin.y + _twolabel.frame.size.height, 80, 20)];
    _confirmLabel.backgroundColor = [UIColor clearColor];
    _confirmLabel.text =@"导师确认";
    _confirmLabel.textColor = [UIColor lightGrayColor];
    _confirmLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_confirmLabel];
    
    
    //    协商时间
    //    小数字
    self.threeLabel = [[UILabel alloc]initWithFrame:CGRectMake(_twoLabelLine.frame.origin.x + _twoLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _threeLabel.layer.masksToBounds = YES;
    _threeLabel.layer.cornerRadius = 7;
    _threeLabel.text = @"3";
    _threeLabel.font = [UIFont systemFontOfSize:10];
    _threeLabel.textAlignment = NSTextAlignmentCenter;
    _threeLabel.textColor = [UIColor whiteColor];
    _threeLabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_threeLabel];
    
    //蓝线
    _threeLabelLine = [[UILabel alloc]initWithFrame:CGRectMake(_threeLabel.frame.origin.x + _threeLabel.frame.size.width, _threeLabel.frame.origin.y + 7 ,_oneLabelLine.frame.size.width, 1)];
    _threeLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_threeLabelLine];
    
    _consultLabel = [[UILabel alloc]initWithFrame:CGRectMake(_threeLabel.frame.origin.x -12, _threeLabel.frame.origin.y + _threeLabel.frame.size.height, 80, 20)];
    _consultLabel.backgroundColor = [UIColor clearColor];
    _consultLabel.text =@"协商时间";
    _consultLabel.textColor = [UIColor lightGrayColor];
    _consultLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_consultLabel];
    
    
    //    服务进行
    //    小数字
    self.fourlabel = [[UILabel alloc]initWithFrame:CGRectMake(_threeLabelLine.frame.origin.x + _threeLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _fourlabel.layer.masksToBounds = YES;
    _fourlabel.layer.cornerRadius = 7;
    _fourlabel.text = @"4";
    _fourlabel.font = [UIFont systemFontOfSize:10];
    _fourlabel.textAlignment = NSTextAlignmentCenter;
    _fourlabel.textColor = [UIColor whiteColor];
    _fourlabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_fourlabel];
    
    //蓝线
    _fourLabelLine = [[UILabel alloc]initWithFrame:CGRectMake(_fourlabel.frame.origin.x + _fourlabel.frame.size.width, _fourlabel.frame.origin.y + 7 ,_oneLabelLine.frame.size.width, 1)];
    _fourLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_fourLabelLine];
    
    _severLabel = [[UILabel alloc]initWithFrame:CGRectMake(_fourlabel.frame.origin.x -12, _fourlabel.frame.origin.y + _fourlabel.frame.size.height, 80, 20)];
    _severLabel.backgroundColor = [UIColor clearColor];
    _severLabel.text =@"服务进行";
    _severLabel.textColor = [UIColor lightGrayColor];
    _severLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_severLabel];
    
    
    //    双方评价
    //    小数字
    _fiveLabel = [[UILabel alloc]initWithFrame:CGRectMake(_fourLabelLine.frame.origin.x + _fourLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _fiveLabel.layer.masksToBounds = YES;
    _fiveLabel.layer.cornerRadius = 7;
    _fiveLabel.text = @"5";
    _fiveLabel.font = [UIFont systemFontOfSize:10];
    _fiveLabel.textAlignment = NSTextAlignmentCenter;
    _fiveLabel.textColor = [UIColor whiteColor];
    _fiveLabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_fiveLabel];
    
    _judgeLabel = [[UILabel alloc]initWithFrame:CGRectMake(_fiveLabel.frame.origin.x -12, _fiveLabel.frame.origin.y + _fiveLabel.frame.size.height, 80, 20)];
    _judgeLabel.backgroundColor = [UIColor clearColor];
    _judgeLabel.text =@"双方评价";
    _judgeLabel.textColor = [UIColor lightGrayColor];
    _judgeLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_judgeLabel];
    
    
    //    查看资讯内容按钮
    self.tutorInspectConsultbutton = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.view addSubview:_tutorInspectConsultbutton];
    
    [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.view.mas_bottom).offset(-100);
        make.left.equalTo(self.view.mas_left).offset(WIDTH / 2 - 70);
        make.right.equalTo(self.view.mas_right).offset(-(WIDTH / 2 - 70 ));
        make.top.equalTo(self.view.mas_top).offset(HEIGHT - 100 - 40);
    }];
    [_tutorInspectConsultbutton setTitleColor:[UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0] forState:UIControlStateNormal];
    [_tutorInspectConsultbutton addTarget:self action:@selector(tutorInspectConsultButtonAction:) forControlEvents:UIControlEventTouchUpInside];

    
if (self.tutorTagForColor == 0) {

    if (([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"0700"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0300"])  ) {
        _tutorContentTitleLabel.text = @"您已经放弃了该订单";
        
    }else{
        //    取消咨询按钮
        self.tutorQuerryCancelbutton = [UIButton buttonWithType:UIButtonTypeCustom];
        [self.view addSubview:_tutorQuerryCancelbutton];
        [self.tutorQuerryCancelbutton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.view.mas_bottom).offset(-150);
            make.left.equalTo(self.view.mas_left).offset(30);
            make.right.equalTo(self.view.mas_right).offset(-(WIDTH - 30 - 120));
            make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
        }];
        [_tutorQuerryCancelbutton setTintColor:[UIColor whiteColor]];
        _tutorQuerryCancelbutton.backgroundColor = [UIColor lightGrayColor];
        _tutorQuerryCancelbutton.layer.masksToBounds = YES;
        _tutorQuerryCancelbutton.layer.cornerRadius = 20;
        
        //    接受咨询
        self.tutorConfirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [self.view addSubview:_tutorConfirmButton];
        [self.tutorConfirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.view.mas_bottom).offset(-150);
            make.left.equalTo(self.tutorQuerryCancelbutton.mas_left).offset(WIDTH - 60 -120);
            make.right.equalTo(self.view.mas_right).offset(-30);
            make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
        }];
        _tutorConfirmButton.layer.masksToBounds = YES;
        _tutorConfirmButton.layer.cornerRadius = 20;
        _tutorConfirmButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        [_tutorConfirmButton addTarget:self action:@selector(confirmAction:) forControlEvents:UIControlEventTouchUpInside];
        
    _tutorContentTitleLabel.text = @"学员已付款，是否接受学员咨询";
    _tutorContentLabel.text = @"若拒绝咨询请选择拒绝接受的理由。";
    
    [_tutorQuerryCancelbutton setTitle:@"取消咨询" forState:UIControlStateNormal];
    [_tutorQuerryCancelbutton addTarget:self action:@selector(cancelConsultButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    
    [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];
    
    [_tutorConfirmButton setTitle:@"接受咨询" forState:UIControlStateNormal];
    
       }
    self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    

    }else if (self.tutorTagForColor == 1){
        
        if ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"1500"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0400"]) {
    
            //   拒绝
            self.tutorQuerryCancelbutton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorQuerryCancelbutton];
            
            [self.tutorQuerryCancelbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.view.mas_left).offset(30);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH - 30 - 120));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            [_tutorQuerryCancelbutton setTintColor:[UIColor whiteColor]];
            _tutorQuerryCancelbutton.backgroundColor = [UIColor lightGrayColor];
            _tutorQuerryCancelbutton.layer.masksToBounds = YES;
            _tutorQuerryCancelbutton.layer.cornerRadius = 20;
            
            //  同意
            self.tutorConfirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorConfirmButton];
            [self.tutorConfirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.tutorQuerryCancelbutton.mas_left).offset(WIDTH - 60 -120);
                make.right.equalTo(self.view.mas_right).offset( -30 );
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            _tutorConfirmButton.layer.masksToBounds = YES;
            _tutorConfirmButton.layer.cornerRadius = 20;
            _tutorConfirmButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
            
        [_tutorQuerryCancelbutton setTitle:@"拒绝" forState:UIControlStateNormal];
        [_tutorQuerryCancelbutton addTarget:self action:@selector(refuseRturnMoneyBUttonAction:) forControlEvents:UIControlEventTouchUpInside];
            
        [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];
            
        [_tutorConfirmButton setTitle:@"同意" forState:UIControlStateNormal];
        [_tutorConfirmButton addTarget:self action:@selector(agreeReturnMoney:) forControlEvents:UIControlEventTouchUpInside];
        
        _tutorContentTitleLabel.text = @"学员申请退款";
            
        }else if ( ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"0700"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"]) ){
            
            _tutorContentTitleLabel.text = @"同意退款";
            _tutorContentLabel.text = @"该单已被学员退订，等待退款中......";

            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        }else if ( ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"1300"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0400"]) ){
            
            _tutorContentTitleLabel.text = @"客服正在介入......";
            _tutorContentLabel.text = @"不同意退款，等待客服进行调解。";

            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        }else{
        _tutorContentTitleLabel.text = @"确认时间";
        _tutorContentLabel.text = @"请尽快和学员确认咨询时间，并在下方输入约定好的时间。";

            //    确认时间
            self.tutorConfirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorConfirmButton];
            [self.tutorConfirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.view.mas_left).offset(WIDTH / 2 - 70);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH / 2 - 70));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            _tutorConfirmButton.layer.masksToBounds = YES;
            _tutorConfirmButton.layer.cornerRadius = 20;
            _tutorConfirmButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        [_tutorConfirmButton setTitle:@"提交时间" forState:UIControlStateNormal];
        [_tutorConfirmButton addTarget:self action:@selector(confirmAction:) forControlEvents:UIControlEventTouchUpInside];

        [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];
        
        [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.view.mas_bottom).offset(-60);
            make.left.equalTo(self.view.mas_left).offset(WIDTH / 2 - 70);
            make.right.equalTo(self.view.mas_right).offset(-(WIDTH / 2 - 70 ));
            make.top.equalTo(self.view.mas_top).offset(HEIGHT - 60 - 40);
            
            }];
            
        self.yearTextF = [[UITextField alloc]initWithFrame:CGRectMake(15, 300, (WIDTH - 20) / 11 *2 , 25)];
        self.yearTextF.layer.borderWidth = 0.5;
        self.yearTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
        self.yearTextF.delegate = self;
        self.yearTextF.text = @"2015";
        [self.view addSubview:self.yearTextF];
        
        UILabel *yearLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.yearTextF.frame.origin.x + self.yearTextF.frame.size.width, self.yearTextF.frame.origin.y, (WIDTH - 20) / 11, 25)];
        yearLabel.text = @"年";
        yearLabel.textColor = [UIColor lightGrayColor];
        [self.view addSubview:yearLabel];
        
        self.mouthTextF = [[UITextField alloc]initWithFrame:CGRectMake(yearLabel.frame.origin.x + yearLabel.frame.size.width, yearLabel.frame.origin.y, (WIDTH - 20) / 11, 25)];
        self.mouthTextF.layer.borderWidth = 0.5;
        self.mouthTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
        self.mouthTextF.delegate = self;
        self.mouthTextF.text = @"11";
        [self.view addSubview:self.mouthTextF];
        
        UILabel *mouthLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.mouthTextF.frame.origin.x + self.mouthTextF.frame.size.width, self.mouthTextF.frame.origin.y,(WIDTH - 20) / 11, 25)];
        mouthLabel.text = @"月";
        mouthLabel.textColor = [UIColor lightGrayColor];
        [self.view addSubview:mouthLabel];
        

        self.dayTextF = [[UITextField alloc]initWithFrame:CGRectMake(mouthLabel.frame.origin.x + mouthLabel.frame.size.width, mouthLabel.frame.origin.y,(WIDTH - 20) / 11, 25)];
        self.dayTextF.layer.borderWidth = 0.5;
        self.dayTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
        self.dayTextF.delegate = self;
        self.dayTextF.text = @"21";
        [self.view addSubview:self.dayTextF];
        
        UILabel *dayLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.dayTextF.frame.origin.x + self.dayTextF.frame.size.width, self.dayTextF.frame.origin.y,(WIDTH - 20) / 11, 25)];
        dayLabel.text = @"日";
        dayLabel.textColor = [UIColor lightGrayColor];
        [self.view addSubview:dayLabel];
 
        self.hourTextF = [[UITextField alloc]initWithFrame:CGRectMake(dayLabel.frame.origin.x + dayLabel.frame.size.width, dayLabel.frame.origin.y,(WIDTH - 20) / 11, 25)];
        self.hourTextF.layer.borderWidth = 0.5;
        self.hourTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
        self.hourTextF.delegate = self;
        self.hourTextF.text = @"15";
        [self.view addSubview:self.hourTextF];
        
        UILabel *hourLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.hourTextF.frame.origin.x + self.hourTextF.frame.size.width, self.hourTextF.frame.origin.y,(WIDTH - 20) / 11, 25)];
        hourLabel.text = @"点";
        hourLabel.textColor = [UIColor lightGrayColor];
        [self.view addSubview:hourLabel];
        
        self.minuteTextF = [[UITextField alloc]initWithFrame:CGRectMake(hourLabel.frame.origin.x + hourLabel.frame.size.width, hourLabel.frame.origin.y,(WIDTH - 20) / 11, 25)];
        self.minuteTextF.layer.borderWidth = 0.5;
        self.minuteTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
        self.minuteTextF.delegate = self;
        self.minuteTextF.text = @"30";
        [self.view addSubview:self.minuteTextF];
        
        UILabel *minuteLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.minuteTextF.frame.origin.x + self.minuteTextF.frame.size.width, self.minuteTextF.frame.origin.y,(WIDTH - 20) / 11, 25)];
        minuteLabel.text = @"分";
        minuteLabel.textColor = [UIColor lightGrayColor];
        [self.view addSubview:minuteLabel];

        
        self.numberOfHour = [[UITextField alloc]initWithFrame:CGRectMake((WIDTH - 100) / 2, hourLabel.frame.origin.y + hourLabel.frame.size.height + 20, 50, 25)];
        self.numberOfHour.layer.borderWidth = 0.5;
        self.numberOfHour.layer.borderColor = [UIColor lightGrayColor].CGColor;
        self.numberOfHour.delegate = self;
        self.numberOfHour.text = @"2";
        [self.view addSubview:self.numberOfHour];
        
        UILabel *numberOfHourLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.numberOfHour.frame.origin.x + self.numberOfHour.frame.size.width + 5, self.numberOfHour.frame.origin.y, 70, 25)];
        numberOfHourLabel.text = @"个小时";
        numberOfHourLabel.textColor = [UIColor lightGrayColor];
        [self.view addSubview:numberOfHourLabel];
        
        }
        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    }else if (self.tutorTagForColor == 2){
        
        
        if (([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"1500"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0500"])) {
            
            // 拒绝退款
            self.tutorQuerryCancelbutton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorQuerryCancelbutton];
            [self.tutorQuerryCancelbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.view.mas_left).offset(30);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH - 30 - 120));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            [_tutorQuerryCancelbutton setTintColor:[UIColor whiteColor]];
            _tutorQuerryCancelbutton.backgroundColor = [UIColor lightGrayColor];
            _tutorQuerryCancelbutton.layer.masksToBounds = YES;
            _tutorQuerryCancelbutton.layer.cornerRadius = 20;
            
            // 同意退款
            self.tutorConfirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorConfirmButton];
            [self.tutorConfirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.tutorQuerryCancelbutton.mas_left).offset(WIDTH - 60 -120);
                make.right.equalTo(self.view.mas_right).offset( -30 );
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            _tutorConfirmButton.layer.masksToBounds = YES;
            _tutorConfirmButton.layer.cornerRadius = 20;
            _tutorConfirmButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
            
            [_tutorQuerryCancelbutton setTitle:@"拒绝" forState:UIControlStateNormal];
            [_tutorQuerryCancelbutton addTarget:self action:@selector(refuseRturnMoneyBUttonAction:) forControlEvents:UIControlEventTouchUpInside];
            [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];
            [_tutorConfirmButton setTitle:@"同意" forState:UIControlStateNormal];
            //        同意退款
            [_tutorConfirmButton addTarget:self action:@selector(agreeReturnMoney:) forControlEvents:UIControlEventTouchUpInside];
            
            self.tutorContentTitleLabel.frame = CGRectMake(30, button.frame.origin.y + button.frame.size.height + 160, self.view.frame.size.width - 60, 30);
            _tutorContentTitleLabel.text = @"学员申请退款";
            
        }else if ( ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"0700"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) ){
            _tutorContentTitleLabel.text = @"等待退款中......";
            _tutorContentLabel.text = @"该订单学员申请退款，您已同意了学员的退款申请。";

            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        }else if ( ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"1300"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"])) {
            _tutorContentTitleLabel.text = @"客服正在介入......";
            _tutorContentLabel.text = @"该订单学员申请退款，您不同意了学员的退款申请，客服即将介入此次交易。";
            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        }else{
        _tutorContentTitleLabel.text = @"请确保在协商时间完成咨询";
        _tutorContentLabel.text = @"时间已经确定，请在规定的时间完成咨询，如果时间有冲突可以取消预约但须说明详细原因。";
        [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];

            //    取消预约
            self.tutorConfirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorConfirmButton];
            [self.tutorConfirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.view.mas_left).offset(WIDTH / 2 - 70);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH / 2 - 70));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            _tutorConfirmButton.layer.masksToBounds = YES;
            _tutorConfirmButton.layer.cornerRadius = 20;
            _tutorConfirmButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
            [_tutorConfirmButton addTarget:self action:@selector(confirmAction:) forControlEvents:UIControlEventTouchUpInside];
            [_tutorConfirmButton setTitle:@"取消预约" forState:UIControlStateNormal];
            
        }
        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.threeLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _threeLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _consultLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    }else if (self.tutorTagForColor == 3){
        
        if ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"0620"]) {
            
            //  拒绝
            self.tutorQuerryCancelbutton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorQuerryCancelbutton];
            
            [self.tutorQuerryCancelbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.view.mas_left).offset(30);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH - 30 - 120));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            
            [_tutorQuerryCancelbutton setTintColor:[UIColor whiteColor]];
            _tutorQuerryCancelbutton.backgroundColor = [UIColor lightGrayColor];
            _tutorQuerryCancelbutton.layer.masksToBounds = YES;
            _tutorQuerryCancelbutton.layer.cornerRadius = 20;
            
            //    同意
            self.tutorConfirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
            
            [self.view addSubview:_tutorConfirmButton];
            
            [self.tutorConfirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.tutorQuerryCancelbutton.mas_left).offset(WIDTH - 60 -120);
                make.right.equalTo(self.view.mas_right).offset( -30 );
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            _tutorConfirmButton.layer.masksToBounds = YES;
            _tutorConfirmButton.layer.cornerRadius = 20;
            _tutorConfirmButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
            
            [_tutorQuerryCancelbutton setTitle:@"拒绝" forState:UIControlStateNormal];
            [_tutorQuerryCancelbutton addTarget:self action:@selector(refuseRturnMoneyBUttonAction:) forControlEvents:UIControlEventTouchUpInside];
            [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];
            [_tutorConfirmButton setTitle:@"同意" forState:UIControlStateNormal];
            //        同意退款
            [_tutorConfirmButton addTarget:self action:@selector(agreeReturnMoney:) forControlEvents:UIControlEventTouchUpInside];
            _tutorContentTitleLabel.text = @"学员申请退款";
            _tutorContentLabel.text = @"学员申请退款您是否同意，请点击下面按钮进行选择。";

        }else if(([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"1000"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(10, 4)] isEqualToString:@"0500"]) ){
            
            self.tutorConfirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
            [self.view addSubview:_tutorConfirmButton];
            [self.tutorConfirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(-150);
                make.left.equalTo(self.view.mas_left).offset(WIDTH / 2 - 70);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH / 2 - 70));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT - 40 - 150);
            }];
            _tutorConfirmButton.layer.masksToBounds = YES;
            _tutorConfirmButton.layer.cornerRadius = 20;
            _tutorConfirmButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
            [_tutorConfirmButton addTarget:self action:@selector(confirmAction:) forControlEvents:UIControlEventTouchUpInside];
        [_tutorConfirmButton setTitle:@"评价学员" forState:UIControlStateNormal];
        [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];
        _tutorContentTitleLabel.text = @"评价学员";
        _tutorContentLabel.text = @"点击评价学员，开始对学员进行评价。";
            

        }else if ( ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"1300"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"]) ){
            _tutorContentTitleLabel.text = @"客服正在介入......";
            _tutorContentLabel.text = @"该订单学员申请退款，您不同意了学员的退款申请，我们的客服即将介入此次交易。";
            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];
        }else if ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"0500"]){
            _tutorContentTitleLabel.text = @"服务进行中......";
            _tutorContentLabel.text = @"服务正在进行，请在规定的时间完成对学员的指导。";
            [_tutorInspectConsultbutton setTitle:@"查看预约详情" forState:UIControlStateNormal];

        }else if ( ([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"0700"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"]) ){
            
            _tutorContentTitleLabel.text = @"等待退款中......";
            _tutorContentLabel.text = @"该订单学员申请退款，您已同意了学员的退款申请。";
            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];
        }else if (([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"1300"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0620"])){
            _tutorContentTitleLabel.text = @"不同意退款";
            _tutorContentLabel.text = @"不同意退款，等待客服介入";
            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];
        }else if(([[self.tutorDetailOrderS substringToIndex:4] isEqualToString:@"0900"] && [[self.tutorDetailOrderS substringWithRange:NSMakeRange(5, 4)] isEqualToString:@"0500"])){
            _tutorContentTitleLabel.text = @"服务完成";
            _tutorContentLabel.text = @"服务完成，等待收款";
            [self.tutorInspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];
        }
        
        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.threeLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _threeLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _consultLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.fourlabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _fourLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _severLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        
    }else  if (self.tutorTagForColor == 5 || self.tutorTagForColor == 4){
            
            _tutorContentTitleLabel.text = @"咨询结束";
            _tutorContentLabel.text = @"咨询已结束，如有疑问请拨打：0571—886415101。";
        
        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.threeLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _threeLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _consultLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.fourlabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _fourLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _severLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _fiveLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _judgeLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
      }

    self.agreeReturnMoneyV = [[agreeReturnMoneyView alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, self.view.frame.size.height)];
    self.agreeReturnMoneyV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    self.agreeReturnMoneyV.mydelegate = self;
    self.agreeReturnMoneyV.AgreeRturnMoneyOrderID = self.orderID;
    [self.view addSubview:self.agreeReturnMoneyV];
    
    self.refuseRturnMoneyV = [[refuseReturnMoneyView alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, self.view.frame.size.height)];
    self.refuseRturnMoneyV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    self.refuseRturnMoneyV.mydelegate = self;
    self.refuseRturnMoneyV.refuseRetrunMoneyID = self.orderID;
    [self.view addSubview:self.refuseRturnMoneyV];
    
    
    self.tutorBaluatioV = [[tutoreBaluationView alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, self.view.frame.size.height)];
    self.tutorBaluatioV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    self.tutorBaluatioV.mydelegate = self;
    self.tutorBaluatioV.tutorBaluationOrderID = self.orderID;
    [self.view addSubview:self.tutorBaluatioV];
    
    
    self.tutorCancelAppionmentV = [[tutorCancelApionmentView alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, self.view.frame.size.height)];
    self.tutorCancelAppionmentV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    self.tutorCancelAppionmentV.mydelegate = self;
    self.tutorCancelAppionmentV.tutorCancelApionmentOrderID = self.orderID;
    [self.view addSubview:self.tutorCancelAppionmentV];
    
    
    self.refuseAceptV = [[refuseAceptOrderView alloc]initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, self.view.frame.size.height)];
    self.refuseAceptV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    self.refuseAceptV.mydelegate = self;
    self.refuseAceptV.refuseAceptOrderID = self.orderID;
    [self.view addSubview:self.refuseAceptV];
    
    // Do any additional setup after loading the view.
   
}

//查看详情
-(void)tutorInspectConsultButtonAction:(UIButton *)button{
    
    inspectConsultDetailViewController *inspectConsultDetailVC = [[inspectConsultDetailViewController alloc]init];
    [self.navigationController pushViewController:inspectConsultDetailVC animated:YES];
}

//取消咨询  弹窗
-(void)cancelConsultButtonAction:(UIButton *)button{
    
    [UIView animateWithDuration:0.5 delay:0.5 usingSpringWithDamping:10 initialSpringVelocity:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        
        self.refuseAceptV.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height);
        
    } completion:^(BOOL finished) {
        
        NSLog(@"动画完成");
        
    }];
}


-(void)refuseAceptOrder{

    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        self.refuseAceptV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
    }];
}

//提交按钮的方法执行
-(void)confirmAction:(UIButton *)button
{
    if ([button.titleLabel.text isEqualToString:@"接受咨询"]) {
        [self confirmAceptbutton];
    }else if ([button.titleLabel.text isEqualToString:@"提交时间"]){
        [self submitTimeButton];
    }else if ([button.titleLabel.text isEqualToString:@"取消预约"]){
        [self tutorCancelAppiontmentButton];
    }else if ([button.titleLabel.text isEqualToString:@"评价学员"]){
        [self ConfirmOverButton];
    }
    else{
    }
}

//导师取消预约  动画弹窗
-(void)tutorCancelAppiontmentButton{

    [UIView animateWithDuration:0.5 delay:0.5 usingSpringWithDamping:10 initialSpringVelocity:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        
        self.tutorCancelAppionmentV.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height);
        
    } completion:^(BOOL finished) {
        
        NSLog(@"动画完成");
        
    }];
}

-(void)tutorCancelAppionmentMethod{
    
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.tutorCancelAppionmentV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
    }];
}

//接受咨询
-(void)confirmAceptbutton{

    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTutorOrderAction:@"acceptOrder" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withTeacherID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"] withOrderID:self.orderID] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self showAlert:@"接受咨询成功"];
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
            } else if([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]){
                
                [self showAlert:@"该单已接受咨询"];
            }
        }
    }];

    self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
}


//提交时间
-(void)submitTimeButton{
    
    NSInteger overTimeInt = [self.numberOfHour.text integerValue] * 60 + [self.hourTextF.text integerValue] * 60 + [self.minuteTextF.text integerValue];
    
    NSInteger overTimeH = overTimeInt / 60;
    NSInteger overTimeM = overTimeInt % 60;
    
    NSString *beginTime = [NSString stringWithFormat:@"%@点%@分",self.hourTextF.text,self.minuteTextF.text];
    NSString *overTime  = [NSString stringWithFormat:@"%2ld点%2ld分",(long)overTimeH,(long)overTimeM];
    
//    
    NSString *ensTime = [NSString stringWithFormat:@"%@年%@月%@日%@-%@",self.yearTextF.text,self.mouthTextF.text,self.dayTextF.text,beginTime,overTime];

    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForEnsureTime:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withTeacherID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"] withOrderID:self.orderID withokTime:ensTime] connectBlock:^(id data) {
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self showAlert:@"确认时间成功"];
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
            } else if([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]) {
                
                [self showAlert:@"时间已经确认"];
            }
        }
    }];
    
    self.threeLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    _threeLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    _consultLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
}


//确认结束

-(void)ConfirmOverButton{
    
    [UIView animateWithDuration:0.5 delay:0.5 usingSpringWithDamping:10 initialSpringVelocity:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        
        self.tutorBaluatioV.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height);
        
    } completion:^(BOOL finished) {
        
        NSLog(@"动画完成");
        
    }];
    _fiveLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    _judgeLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
}

//确认结束  取消弹窗
-(void)cancelTutorBaluation{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.tutorBaluatioV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
    }];
    _fiveLabel.backgroundColor = [UIColor lightGrayColor];
    _judgeLabel.textColor = [UIColor lightGrayColor];
}

//拒绝退款  动画弹窗
-(void)refuseRturnMoneyBUttonAction:(UIButton *)button{
    
    [UIView animateWithDuration:0.5 delay:0.5 usingSpringWithDamping:10 initialSpringVelocity:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        
        self.refuseRturnMoneyV.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height);
        
    } completion:^(BOOL finished) {
        
        NSLog(@"动画完成");
        
    }];
}
    
//弹窗取消
-(void)cancelRefuseReturnMoney{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.refuseRturnMoneyV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
        
    }];
}


//同意退款 动画弹窗

-(void)agreeReturnMoney:(UIButton *)button{
    
    [UIView animateWithDuration:0.5 delay:0.5 usingSpringWithDamping:10 initialSpringVelocity:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        
        self.agreeReturnMoneyV.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height);
        
    } completion:^(BOOL finished) {
        
        NSLog(@"动画完成");
        
    }];
}


//弹窗回收
-(void)cancelAgreeReturnM{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.agreeReturnMoneyV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {

    }];
}


//撤销键盘
-(void)backgroundTapped:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}


#pragma mark -- textFieldDelegate
-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.view.frame = CGRectMake(0, -150, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

-(void)textFieldDidEndEditing:(UITextField *)textField
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.view.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

-(void)tutorDetailQuerryInfoBackbutton:(UIButton *)button{

    [self.navigationController popToRootViewControllerAnimated:YES];
}

//警示框回弹自动   
- (void) showAlert:(NSString *)message{//时间
    
    UIAlertView *promptAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:message delegate:nil cancelButtonTitle:nil otherButtonTitles:nil];
    
    [NSTimer scheduledTimerWithTimeInterval:1.0f
                                     target:self
                                   selector:@selector(timerFireMethod:)
                                   userInfo:promptAlert
                                    repeats:YES];
    [promptAlert show];
}

- (void)timerFireMethod:(NSTimer *)theTimer//弹出框
{
    UIAlertView *promptAlert = (UIAlertView * )[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
}

#pragma mark -- alertviewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
        
        if (buttonIndex == 0) {
            
            [self.navigationController popToRootViewControllerAnimated:YES];
        } else if (buttonIndex == 1) {
            
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            loginVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
            
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
