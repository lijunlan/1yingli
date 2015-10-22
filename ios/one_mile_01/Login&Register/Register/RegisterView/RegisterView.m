//
//  RegisterView.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "RegisterView.h"


@interface RegisterView ()<UITextFieldDelegate>

@property (nonatomic, strong) UIButton *registerBT;
@property (nonatomic, strong) UIButton *sendTestBT;
@property (nonatomic, strong) UIButton *loginBT;

@end

@implementation RegisterView
-(instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    
    UIImage *backImage = [UIImage imageNamed:@"loginBG375.png"];
    UIImageView *backView = [[UIImageView alloc]initWithFrame:[[UIScreen mainScreen]bounds]];
    backView.image = backImage;
    [self addSubview:backView];
    
    UILabel *titleLabel = [[UILabel alloc]init];
    [self addSubview:titleLabel];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.mas_centerX);
        make.top.equalTo(self.mas_top).offset(30);
        make.height.offset(20);
    }];
    titleLabel.text = @"注册新用户";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];
    
    
    UIImage *popImage = [UIImage imageNamed:@"pop_back.png"];
    UIButton *backBT = [UIButton buttonWithType:UIButtonTypeCustom];
    [self addSubview:backBT];

    [backBT mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.mas_left).offset(20);
        make.top.equalTo(self.mas_top).offset(20);
        make.height.offset(PUSHANDPOPBUTTONSIZE + 20);
        make.width.offset (PUSHANDPOPBUTTONSIZE + 20);
    }];
    [backBT setBackgroundImage:popImage forState:UIControlStateNormal];
    backBT.backgroundColor = [UIColor clearColor];
    [backBT addTarget:self action:@selector(registerAction:) forControlEvents:UIControlEventTouchUpInside];

    self.nameLabel = [[UILabel alloc]init];
    [self addSubview:self.nameLabel];
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.mas_top).offset(90);
        make.left.equalTo(self.mas_left).offset(40);
        make.right.equalTo(self.mas_right).offset(-40);
        make.height.offset(20);
        if (iPhone4s) {
            make.top.equalTo(self.mas_top).offset(65);
        }
    }];
    
    self.nameLabel.text = @"昵称";
    self.nameLabel.textColor = [UIColor lightGrayColor];
    self.nameLabel.font = [UIFont systemFontOfSize:16];
    self.nameLabel.backgroundColor = [UIColor clearColor];


    self.nameTextFile = [[UITextField alloc]init];
    [self addSubview:self.nameTextFile];
    [self.nameTextFile mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.nameLabel.mas_top).offset(30);
        make.left.equalTo(self.mas_left).offset(20);
        make.right.equalTo(self.mas_right).offset(-20);
        if (iPhone6P) {
            make.height.offset(50);
        }else if (iPhone4s){
            make.height.offset(30);
        }
        else{
        make.height.offset(35);
        }
        
    }];
    self.nameTextFile.backgroundColor = [UIColor clearColor];
    self.nameTextFile.textColor = [UIColor lightGrayColor];
    self.nameTextFile.layer.cornerRadius = 0;
    [self.nameTextFile.layer setBorderWidth:1];
    [self.nameTextFile.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];


    self.iphoneLabel = [[UILabel alloc]init];
    [self addSubview:self.iphoneLabel];
    [self.iphoneLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone6P) {
            make.top.equalTo(self.nameTextFile.mas_top).offset(60);
        }else if (iPhone4s){
            make.top.equalTo(self.nameTextFile.mas_top).offset(35);
        }
        else{
            make.top.equalTo(self.nameTextFile.mas_top).offset(45);
        }
        make.left.equalTo(self.nameLabel.mas_left).offset(0);
        make.right.equalTo(self.nameLabel.mas_right).offset(0);
        make.height.offset(20);
    }];
    self.iphoneLabel.text = @"绑定手机号或邮箱";
    self.iphoneLabel.textColor = [UIColor lightGrayColor];
    self.iphoneLabel.font = [UIFont systemFontOfSize:16];
    self.iphoneLabel.backgroundColor = [UIColor clearColor];

    
    self.iphoneTextFile = [[UITextField alloc]init];
    [self addSubview:self.iphoneTextFile];
    [self.iphoneTextFile mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.iphoneLabel.mas_top).offset(30);
        make.left.equalTo(self.mas_left).offset(20);
        make.right.equalTo(self.mas_right).offset(-20);
        if (iPhone6P) {
            make.height.offset(50);
        }else if (iPhone4s){
            make.height.offset(30);
        }
        else{
            make.height.offset(35);
        }
    }];
    self.iphoneTextFile.backgroundColor = [UIColor clearColor];
    self.iphoneTextFile.textColor = [UIColor lightGrayColor];
    self.iphoneTextFile.layer.cornerRadius = 0;
    [self.iphoneTextFile.layer setBorderWidth:1];
    [self.iphoneTextFile.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];
    
    
    
    self.sendTestBT = [[UIButton alloc]init];
    [self addSubview:self.sendTestBT];
    [self.sendTestBT mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone6P) {
            make.top.equalTo(self.iphoneTextFile.mas_top).offset(65);
            make.height.offset(50);
        }else if (iPhone4s){
            make.top.equalTo(self.iphoneTextFile.mas_top).offset(45);
            make.height.offset(35);
        }else{
            make.top.equalTo(self.iphoneTextFile.mas_top).offset(50);
            make.height.offset(40);
        }
        make.left.equalTo(self.mas_left).offset(20);
        make.width.offset(self.frame.size.width / 2 - 20);
    }];
    [self.sendTestBT setTitle:@"点击发送验证码" forState:UIControlStateNormal];
    self.sendTestBT.titleLabel.font = [UIFont systemFontOfSize:16];
    [self.sendTestBT setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    self.sendTestBT.layer.cornerRadius = 6;
    [self.sendTestBT.layer setBorderWidth:1];
    [self.sendTestBT.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];
    self.sendTestBT.backgroundColor = [UIColor clearColor];
    
//    触发验证码
    [self.sendTestBT addTarget:self action:@selector(checkAction:) forControlEvents:UIControlEventTouchUpInside];
    
    
    self.passWordTextFile = [[UITextField alloc]init];
    [self addSubview:self.passWordTextFile];
        [self.passWordTextFile mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone6P) {
            make.height.offset(50);
        }else if (iPhone4s){
            make.height.offset(35);
        }else{
            make.height.offset(40);
            }
        make.top.equalTo(self.sendTestBT.mas_top).offset(0);
        make.right.equalTo(self.sendTestBT.mas_right).offset( self.frame.size.width / 2  - 20);
        make.width.offset(self.frame.size.width / 2 - 40);
    }];
    self.passWordTextFile.textColor = [UIColor lightGrayColor];
    self.passWordTextFile.layer.cornerRadius = 0;
    [self.passWordTextFile.layer setBorderWidth:1];
    [self.passWordTextFile.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];
    self.passWordTextFile.backgroundColor = [UIColor clearColor];
    
    
    self.setPassLabel = [[UILabel alloc]init];
    [self addSubview:self.setPassLabel];
    [self.setPassLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone6P) {
            make.top.equalTo(self.sendTestBT.mas_top).offset(60);
        }else if (iPhone4s){
            make.top.equalTo(self.sendTestBT.mas_top).offset(45);
        }else{
            make.top.equalTo(self.sendTestBT.mas_top).offset(50);
        }

        make.left.equalTo(self.mas_left).offset(40);
        make.right.equalTo(self.mas_right).offset(-40);
        make.height.offset(20);
    }];
    self.setPassLabel.text = @"设置密码";
    self.setPassLabel.font = [UIFont systemFontOfSize:16];
    self.setPassLabel.textColor = [UIColor lightGrayColor];
    self.setPassLabel.backgroundColor = [UIColor clearColor];
    
    
    self.setPassWordTextFile = [[UITextField alloc]initWithFrame:CGRectMake(self.setPassLabel.frame.origin.x ,self.setPassLabel.frame.origin.y + self.setPassLabel.frame.size.height + 5,self.setPassLabel.frame.size.width,self.nameTextFile.frame.size.height )];
    
    self.setPassWordTextFile = [[UITextField alloc]init];
    [self addSubview:self.setPassWordTextFile];
    [self.setPassWordTextFile mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone6P) {
            make.height.offset(50);
        }else if (iPhone4s){
            make.height.offset(30);
        }else{
            make.height.offset(35);
        }
        make.top.equalTo(self.setPassLabel.mas_top).offset(30);
        make.left.equalTo(self.mas_left).offset(20);
        make.right.equalTo(self.mas_right).offset(-20);
    }];
    self.setPassWordTextFile.secureTextEntry = YES;
    self.setPassWordTextFile.textColor = [UIColor lightGrayColor];
    self.setPassWordTextFile.backgroundColor = [UIColor clearColor];
    self.setPassWordTextFile.layer.cornerRadius = 0;
    [self.setPassWordTextFile.layer setBorderWidth:1];
    [self.setPassWordTextFile.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];
    
    
    self.confirmLabel = [[UILabel alloc]init];
    [self addSubview:self.confirmLabel];
    [self.confirmLabel mas_makeConstraints:^(MASConstraintMaker *make) {

        if (iPhone6P) {
            make.top.equalTo(self.setPassWordTextFile.mas_top).offset(60);
        }else if (iPhone4s){
            make.top.equalTo(self.setPassWordTextFile.mas_top).offset(40);
        }else{
            make.top.equalTo(self.setPassWordTextFile.mas_top).offset(45);
        }
        make.left.equalTo(self.mas_left).offset(40);
        make.right.equalTo(self.mas_right).offset(-40);
        make.height.offset(20);
    }];
    self.confirmLabel.text = @"确认密码";
    self.confirmLabel.font = [UIFont systemFontOfSize:16];
    self.confirmLabel.backgroundColor = [UIColor clearColor];
    self.confirmLabel.textColor = [UIColor lightGrayColor];
    
    
    self.confirmTextFile = [[UITextField alloc]init];
    [self addSubview:self.confirmTextFile];
    [self.confirmTextFile mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.confirmLabel.mas_top).offset(30);
        make.left.equalTo(self.mas_left).offset(20);
        make.right.equalTo(self.mas_right).offset(-20);
        if (iPhone6P) {
            make.height.offset(50);
        }else if (iPhone4s){
            make.height.offset(30);
        }else{
            make.height.offset(35);
        }
    }];
    
    self.confirmTextFile.secureTextEntry = YES;
    self.confirmTextFile.backgroundColor = [UIColor clearColor];
    self.confirmTextFile.textColor = [UIColor lightGrayColor];
    self.confirmTextFile.layer.cornerRadius = 0;
    [self.confirmTextFile.layer setBorderWidth:1];
    [self.confirmTextFile.layer setBorderColor:[[UIColor lightGrayColor] CGColor]];
    
    
    UIImageView *buttonImageView = [[UIImageView alloc] init];
    buttonImageView.image = [UIImage imageNamed:@"loginBT.png"];
    [self addSubview:buttonImageView];
    [buttonImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.mas_left).offset(90);
        make.right.equalTo(self.mas_right).offset(-90);
        if (iPhone5) {
            make.top.equalTo(self.confirmTextFile.mas_top).offset(50);
            make.height.offset(45);
        }else if(iPhone6){
            make.top.equalTo(self.confirmTextFile.mas_top).offset(80);
            make.height.offset(50);
        }else if (iPhone6P){
            make.top.equalTo(self.confirmTextFile.mas_top).offset(100);
            make.height.offset(55);
        }else if (iPhone4s){
            make.top.equalTo(self.confirmTextFile.mas_top).offset(40);
            make.height.offset(40);
        }
    }];
    
    self.registerBT= [UIButton buttonWithType:UIButtonTypeCustom];
    [self addSubview:self.registerBT];
    [self.registerBT mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone5) {
            make.top.equalTo(self.confirmTextFile.mas_top).offset(50);
            make.height.offset(45);
        }else if(iPhone6){
            make.top.equalTo(self.confirmTextFile.mas_top).offset(80);
            make.height.offset(50);
        }else if (iPhone6P){
            make.top.equalTo(self.confirmTextFile.mas_top).offset(100);
            make.height.offset(55);
        }else if (iPhone4s){
            make.top.equalTo(self.confirmTextFile.mas_top).offset(40);
            make.height.offset(40);
        }
        make.left.equalTo(self.mas_left).offset(90);
        make.right.equalTo(self.mas_right).offset(-90);
    }];
    [self.registerBT setTitle:@"注  册" forState:UIControlStateNormal];
    self.registerBT.titleLabel.font =[UIFont systemFontOfSize:16];
    [self.registerBT addTarget:self action:@selector(registerAction:) forControlEvents:UIControlEventTouchUpInside];
    
    
    UILabel *label = [[UILabel alloc]init];
    label.backgroundColor = [UIColor lightGrayColor];
    [self addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone4s) {
            make.bottom.equalTo(self.mas_bottom).offset(-40);
        }else{
        make.bottom.equalTo(self.mas_bottom).offset(-60);
        }
        make.left.equalTo(self.mas_left).offset(5);
        make.right.equalTo(self.mas_right).offset(-5);
        make.height.offset(0.5);
    }];
    
    UILabel *bottomL = [[UILabel alloc] init];
    [self addSubview:bottomL];
    [bottomL mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone4s) {
            make.bottom.equalTo(self.mas_bottom).offset(-10);
        }else{
            make.bottom.equalTo(self.mas_bottom).offset(-20);
        }
        make.left.equalTo(self.mas_centerX ).offset(-70);
        make.height.offset(20);
    }];
    bottomL.text = @"已有账号, 去";
    bottomL.textColor = [UIColor colorWithRed:136 / 255.0 green:137 / 255.0 blue:138 / 255.0 alpha:1.0];

    self.loginBT = [UIButton buttonWithType:UIButtonTypeCustom];
    [self addSubview:_loginBT];
    [self.loginBT mas_makeConstraints:^(MASConstraintMaker *make) {
        if (iPhone4s) {
            make.bottom.equalTo(self.mas_bottom).offset(20);
        }else{
        make.bottom.equalTo(self.mas_bottom).offset(10);
        }
        make.right.equalTo(bottomL.mas_right).offset(50);
        make.height.offset(80);
    }];
    [self.loginBT setTitle:@"登录" forState:UIControlStateNormal];
    [self.loginBT setTitleColor:[UIColor colorWithRed:(44 % 255) / 255.0 green:(97 % 255) / 255.0 blue:(128 % 255) /255.0 alpha:1.0] forState:UIControlStateNormal];
    [self.loginBT addTarget:self action:@selector(registerAction:) forControlEvents:UIControlEventTouchUpInside];
    _loginBT.titleLabel.font = [UIFont systemFontOfSize:16.5];
    _loginBT.titleLabel.font = [UIFont boldSystemFontOfSize:20.0f];


    UIGestureRecognizer *tapG = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(resignKeyboard:)];
    [self addGestureRecognizer:tapG];
    
    self.setPassWordTextFile.delegate = self;
    self.confirmTextFile.delegate = self;
}

-(void)resignKeyboard:(UITapGestureRecognizer *)tap
{
    [self endEditing:YES];
}

#pragma mark -- 验证码
-(void)checkAction:(UIButton *)button
{
    [self.sendTestBT setTitle:@"正在发送..." forState:UIControlStateNormal];
    [self.sendTestBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.sendTestBT setBackgroundColor:[UIColor colorWithRed:67 / 255.0 green:172 / 255.0 blue:229 / 255.0 alpha:1.0]];
    [self getCheckNo:self.iphoneTextFile.text];
}

-(void)getCheckNo:(NSString *)username
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForCheckNo:self.iphoneTextFile.text] connectBlock:^(id data) {
    
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        NSLog(@"%@", dic);
        NSString *state = [dic objectForKey:@"state"];
            
        if ([state isEqualToString:@"success"]) {
            
            [self setAlertView:@"提示" withMessage:@"验证码已发送成功，注意查收验证码" buttonTitle:@"确认"];
            
            [self.sendTestBT setTitle:@"点击发送验证码" forState:UIControlStateNormal];
            [self.sendTestBT setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
            [self.sendTestBT setBackgroundColor:[UIColor clearColor]];
        } else {
            
            [self setAlertView:@"提示" withMessage:@"验证码获取失败" buttonTitle:@"确认"];
            
            [self.sendTestBT setTitle:@"点击发送验证码" forState:UIControlStateNormal];
            [self.sendTestBT setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
            [self.sendTestBT setBackgroundColor:[UIColor clearColor]];
        }
        
    }];
}

#pragma mark -- 注册 & 登录
-(void)registerAction:(UIButton *)button
{
    if ([button.titleLabel.text isEqualToString:@"注  册"]) {
        
        [self endEditing:YES];
        
        if (([self isValidateEmail:self.iphoneTextFile.text] || [self isValidateMobile:self.iphoneTextFile.text]) && [self isValidatePassword:self.setPassWordTextFile.text]) {
            
            if ([self.setPassWordTextFile.text isEqualToString:self.confirmTextFile.text]) {
                
                [self.myDelegate registerOne:self.iphoneTextFile.text withPwd:self.setPassWordTextFile.text withCheckNo:self.passWordTextFile.text withNikName:self.nameTextFile.text];
            } else {
            
                [self setAlertView:@"提示" withMessage:@"输入的密码不一致，请确认后再输" buttonTitle:@"确认"];
            }
            
        } else {
        
            if ([self isValidateEmail:self.iphoneTextFile.text] || [self isValidateMobile:self.iphoneTextFile.text]) {
                
                [self setAlertView:@"提示" withMessage:@"输入的邮箱或手机号格式不正确" buttonTitle:@"确认"];
                
            } else if ([self isValidatePassword:self.setPassWordTextFile.text]) {
            
                [self setAlertView:@"提示" withMessage:@"您输入的密码只能是数字或字母" buttonTitle:@"确认"];
            }
        }
        
    } else {
    
        [self.myDelegate backToLogin];
    }
}

#pragma mark -- 有效手机号和邮箱 密码
//邮箱验证
-(BOOL)isValidateEmail:(NSString *)email
{
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    
    return [emailTest evaluateWithObject:email];
}

//手机号验证
-(BOOL)isValidateMobile:(NSString *)mobile
{
    //手机号以13， 15，18开头，八个 \d 数字字符
    NSString *phoneRegex = @"^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
    NSPredicate *phoneTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",phoneRegex];
    
    //    NSLog(@"phoneTest is %@",phoneTest);
    return [phoneTest evaluateWithObject:mobile];
}

//密码验证
-(BOOL)isValidatePassword:(NSString *)password
{
    NSString *passRegex = @"[A-Za-z0-9]{6,20}";
    NSPredicate *passText = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", passRegex];
    return [passText evaluateWithObject:password];
}

#pragma mark -- 提示框
-(void)setAlertView:(NSString *)title withMessage:(NSString *)message buttonTitle:(NSString *)buttonTitle
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message:message delegate:self cancelButtonTitle:buttonTitle otherButtonTitles:nil];
    [self addSubview:alert];
    
    [alert show];
}

#pragma mark -- UITextFieldDelegate
-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.5f];
    
    self.frame = CGRectMake(0, -120, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

-(void)textFieldDidEndEditing:(UITextField *)textField
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

@end
