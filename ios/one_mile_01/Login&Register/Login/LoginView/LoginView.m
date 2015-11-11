//
//  LoginView.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "LoginView.h"

@interface LoginView ()<UITextFieldDelegate>

@property (nonatomic, strong) UIButton *loginBT;
@property (nonatomic, strong) UIButton *loseBT;

@property (nonatomic, strong) UIButton *toRegBT;

@end

@implementation LoginView

-(instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    UIImage *bg_image = [UIImage imageNamed:@"loginBG375.png"];
    bg_image = [bg_image imageByScalingToSize:CGSizeMake(WIDTH + 0.5, HEIGHT)];
    UIColor *bg_color = [UIColor colorWithPatternImage:bg_image];
    self.backgroundColor = bg_color;
    
    UIImage *popImage = [UIImage imageNamed:@"pop_back.png"];
    UIButton *backBT = [UIButton buttonWithType:UIButtonTypeCustom];
    //backBT.frame = CGRectMake(20, 35, PUSHANDPOPBUTTONSIZE + 20, PUSHANDPOPBUTTONSIZE + 20);
    [backBT setBackgroundImage:popImage forState:UIControlStateNormal];
    backBT.backgroundColor = [UIColor clearColor];
    [backBT addTarget:self action:@selector(backAction:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:backBT];
    
    [backBT mas_makeConstraints:^(MASConstraintMaker *make) {
        
        make.left.equalTo(self).offset(20);
        make.top.equalTo(self).offset(20);
        make.width.offset(PUSHANDPOPBUTTONSIZE + 20);
        make.height.offset(PUSHANDPOPBUTTONSIZE + 20);
    }];
    
    CGFloat originH;
    if (iPhone5) {
        originH = HEIGHT / 2.0 - 60;
    } else {
    
        originH = HEIGHT / 2.0 - 90;
    }
    self.idLabel = [[UILabel alloc] initWithFrame:CGRectMake(50, originH, WIDTH - 100, 50)];
    //self.idLabel = [[UILabel alloc] init];
    self.idLabel.backgroundColor = [UIColor blackColor];
    self.idLabel.alpha = 0.4;
    [self addSubview:_idLabel];
    
    UIImageView *logoImageView = [[UIImageView alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 70, _idLabel.frame.origin.y - 100, 140, 48)];
    //UIImageView *logoImageView = [[UIImageView alloc] init];
    logoImageView.image = [UIImage imageNamed:@"loginOnemile.png"];
    [self addSubview:logoImageView];
    
    UIImageView *idImageView = [[UIImageView alloc] initWithFrame:CGRectMake(_idLabel.frame.origin.x + 12, _idLabel.frame.origin.y + 6, 32, _idLabel.frame.size.height - 12)];
    //UIImageView *idImageView = [[UIImageView alloc] init];
    idImageView.image = [UIImage imageNamed:@"loginID.png"];
    [self addSubview:idImageView];
    
    
    //idImageView的x轴坐标加的12 + idTF的X轴坐标加的20
    self.idTF = [[UITextField alloc] initWithFrame:CGRectMake(idImageView.frame.origin.x + idImageView.frame.size.width + 20, _idLabel.frame.origin.y, _idLabel.bounds.size.width - idImageView.frame.size.width - 32, _idLabel.frame.size.height)];
    //self.idTF = [[UITextField alloc] init];
    //self.idTF.backgroundColor = [UIColor yellowColor];
    self.idTF.clearsOnBeginEditing = YES;
    self.idTF.textColor = [UIColor darkGrayColor];
    self.idTF.text = @"用户手机号或邮箱";
    [self addSubview:_idTF];
    
    self.passLabel = [[UILabel alloc] initWithFrame:CGRectMake(_idLabel.frame.origin.x, _idLabel.frame.origin.y + _idLabel.frame.size.height + 20, _idLabel.frame.size.width, _idLabel.frame.size.height)];
    //self.passLabel = [[UILabel alloc] init];
    self.passLabel.backgroundColor = [UIColor blackColor];
    self.passLabel.alpha = 0.4;
    [self addSubview:_passLabel];
    
//    [logoImageView mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        if (iPhone5) {
//            make.top.equalTo(backBT.mas_bottom).offset(60);
//            make.bottom.equalTo(_idLabel.mas_top).insets(UIEdgeInsetsMake(60, WIDTH / 2.0 - 70, 30, WIDTH / 2.0 - 70));
//        } else if (iPhone6){
//            make.top.equalTo(backBT.mas_bottom).offset(80);
//            make.bottom.equalTo(_idLabel.mas_top).insets(UIEdgeInsetsMake(80, WIDTH / 2.0 - 70, 100, WIDTH / 2.0 - 70));
//        } else {
//            make.top.equalTo(backBT.mas_bottom).offset(120);
//            make.bottom.equalTo(_idLabel.mas_top).insets(UIEdgeInsetsMake(120, WIDTH / 2.0 - 70, 140, WIDTH / 2.0 - 70));
//        }
//        make.centerX.equalTo(self.mas_centerX);
//        make.width.equalTo(@140);
//    }];
//    
//    [_idLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        UIEdgeInsets padding;
//        if (iPhone5) {
//            padding = UIEdgeInsetsMake(60, 20, 20, 20);
//        } else {
//            padding = UIEdgeInsetsMake(100, 20, 20, 20);
//        }
//        
//        make.top.equalTo(logoImageView.mas_bottom).insets(padding);
//        make.left.equalTo(self).offset(20);
//        make.right.equalTo(self).offset(-20);
//        make.bottom.equalTo(_passLabel.mas_top).insets(padding);
//        make.height.equalTo(@50);
//    }];
//    
//    [idImageView mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.left.equalTo(_idLabel.mas_left).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.top.equalTo(_idLabel.mas_top).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.bottom.equalTo(_idLabel.mas_bottom).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.right.equalTo(_idTF.mas_left).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.width.equalTo(@32);
//    }];
//    
//    [_idTF mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.top.equalTo(_idLabel).offset(6);
//        make.bottom.equalTo(_idLabel).offset(-6);
//        make.left.equalTo(idImageView.mas_right).insets(UIEdgeInsetsMake(6, 10, 6, 5));
//        make.right.equalTo(_idLabel.mas_right).offset(-5);
//    }];
//    
//    [_passLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.top.equalTo(_idLabel.mas_bottom).insets(UIEdgeInsetsMake(20, 20, 0, 20));
//        make.left.equalTo(self).offset(20);
//        make.width.equalTo(_idLabel);
//        make.height.equalTo(_idLabel);
//    }];
    
    UIImageView *passImageView = [[UIImageView alloc] initWithFrame:CGRectMake(_passLabel.frame.origin.x + 12, _passLabel.frame.origin.y + 6, 32, _passLabel.frame.size.height - 12)];
    passImageView.image = [UIImage imageNamed:@"loginPW.png"];
    [self addSubview:passImageView];
    
    self.passTF = [[UITextField alloc] initWithFrame:CGRectMake(passImageView.frame.origin.x + passImageView.frame.size.width + 20, _passLabel.frame.origin.y, _passLabel.bounds.size.width - passImageView.frame.size.width - 32, _passLabel.frame.size.height)];
    self.passTF.placeholder = @"用户密码";
    self.passTF.secureTextEntry = YES;
    self.passTF.clearsOnBeginEditing = YES;
    self.passTF.textColor = [UIColor darkGrayColor];
    [self addSubview:_passTF];
    
//    [passImageView mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.top.equalTo(_passLabel.mas_top).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.bottom.equalTo(_passLabel.mas_bottom).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.left.equalTo(_passLabel.mas_left).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.right.equalTo(_passTF.mas_left).insets(UIEdgeInsetsMake(6, 12, 6, 10));
//        make.width.equalTo(idImageView);
//    }];
//    
//    [_passTF mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.top.equalTo(_passLabel).offset(6);
//        make.bottom.equalTo(_passLabel).offset(-6);
//        make.left.equalTo(passImageView.mas_right).insets(UIEdgeInsetsMake(6, 10, 6, 5));
//        make.right.equalTo(_passLabel.mas_right).offset(-5);
//    }];
    
    //登录背景
//    UIImageView *buttonImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"loginBT.png"]];
//    buttonImageView.frame = CGRectMake(_passLabel.frame.origin.x + 30, _passLabel.frame.origin.y + _passLabel.frame.size.height + 20, _passLabel.frame.size.width - 60, _passLabel.frame.size.height);
//    buttonImageView.userInteractionEnabled = YES;
//    [self addSubview:buttonImageView];
    
    self.loginBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.loginBT.frame = CGRectMake(_passLabel.frame.origin.x, _passLabel.frame.origin.y + _passLabel.frame.size.height + 10, _passLabel.frame.size.width, _passLabel.frame.size.height);
    self.loginBT.backgroundColor = [UIColor colorWithRed:67 / 255.0 green:172 / 255.0 blue:229 / 255.0 alpha:1.0];
    [self.loginBT setTitle:@"登  录" forState:UIControlStateNormal];
    [self.loginBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//    self.loginBT.titleLabel.font = [UIFont boldSystemFontOfSize:16.0f];
    self.loginBT.layer.masksToBounds = YES;
    self.loginBT.layer.cornerRadius = 2.0f;
    [_loginBT addTarget:self action:@selector(loginAction:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:_loginBT];
    
    self.loseBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.loseBT.frame = CGRectMake(_loginBT.frame.origin.x + 30, _loginBT.frame.origin.y + _loginBT.frame.size.height, _loginBT.frame.size.width - 60, _loginBT.frame.size.height - 10);
    self.loseBT.titleLabel.text = @"忘记密码";
    [self.loseBT setTitle:@"忘记密码" forState:UIControlStateNormal];
    [self.loseBT setTitleColor:[UIColor colorWithRed:136 / 255.0 green:137 / 255.0 blue:138 / 255.0 alpha:1.0] forState:UIControlStateNormal];
    _loseBT.titleLabel.font = [UIFont systemFontOfSize:16.0];
    _loseBT.titleLabel.font = [UIFont boldSystemFontOfSize:15.0f];
    self.loseBT.backgroundColor = [UIColor clearColor];
    [_loseBT addTarget:self action:@selector(loginAction:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:_loseBT];
    
//    [buttonImageView mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.top.equalTo(_passLabel.mas_bottom).insets(UIEdgeInsetsMake(20, 0, 0, 0));
//        make.left.equalTo(self).offset(50);
//        make.right.equalTo(self).offset(-50);
//        make.height.equalTo(_passLabel);
//    }];
//    
//    [_loginBT mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.top.equalTo(buttonImageView.mas_topMargin);
//        make.left.equalTo(buttonImageView).insets(UIEdgeInsetsMake(0, 10, 0, 10));
//        make.right.equalTo(buttonImageView).insets(UIEdgeInsetsMake(0, 10, 0, 10));
//        make.bottom.equalTo(buttonImageView.mas_bottomMargin);
//    }];
//    
//    [_loseBT mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.centerX.equalTo(_loginBT.mas_centerX);
//        make.top.equalTo(buttonImageView.mas_bottom).insets(UIEdgeInsetsMake(0, 0, 0, 0));
//        make.width.equalTo(_loginBT);
//        make.height.equalTo(_loginBT);
//    }];
    
    UIButton *loginWayForWechat = [UIButton buttonWithType:UIButtonTypeCustom];
    if (iPhone5) {
        loginWayForWechat.frame = CGRectMake(WIDTH / 2.0 - 75, _loseBT.frame.origin.y + _loseBT.frame.size.height, 70, 65);
    }else if (iPhone4s){
        loginWayForWechat.frame = CGRectMake(WIDTH / 2.0 - 75, _loseBT.frame.origin.y + _loseBT.frame.size.height, 60, 55);
    }else {
        loginWayForWechat.frame = CGRectMake(WIDTH / 2.0 - 105, _loseBT.frame.origin.y + _loseBT.frame.size.height + 20, 100, 95);
    }
    [loginWayForWechat setBackgroundImage:[UIImage imageNamed:@"loginWay_wechat.png"] forState:UIControlStateNormal];
    [self addSubview:loginWayForWechat];
    
    [loginWayForWechat addTarget:self action:@selector(wechatLogin:) forControlEvents:UIControlEventTouchUpInside];
    
    UIButton *loginWayForWeibo = [UIButton buttonWithType:UIButtonTypeCustom];
    loginWayForWeibo.frame = CGRectMake(WIDTH / 2.0 + 5, loginWayForWechat.frame.origin.y, loginWayForWechat.frame.size.width, loginWayForWechat.frame.size.height);
    [loginWayForWeibo setBackgroundImage:[UIImage imageNamed:@"loginWay_weibo.png"] forState:UIControlStateNormal];
    [self addSubview:loginWayForWeibo];
    
    [loginWayForWeibo addTarget:self action:@selector(weiboLogin:) forControlEvents:UIControlEventTouchUpInside];
    
    CGFloat interForHorizen;
    if (iPhone5) {
        interForHorizen = 15;
    }else if (iPhone4s){
        interForHorizen = 10;
    }else {
        interForHorizen = 30;
    }
    
    UILabel *line = [[UILabel alloc] initWithFrame:CGRectMake(30, loginWayForWechat.frame.origin.y + loginWayForWechat.frame.size.height + interForHorizen, WIDTH - 60, 0.5)];
    //UILabel *line = [[UILabel alloc] init];
    line.backgroundColor = [UIColor lightGrayColor];
    [self addSubview:line];
    
    UILabel *bottomL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 60, line.frame.origin.y + 10, 85, 40)];
    //UILabel *bottomL = [[UILabel alloc] init];
    //bottomL.backgroundColor = [UIColor yellowColor];
    bottomL.text = @"新用户？去";
    bottomL.textColor = [UIColor colorWithRed:136 / 255.0 green:137 / 255.0 blue:138 / 255.0 alpha:1.0];
    [self addSubview:bottomL];
    
    self.toRegBT = [UIButton buttonWithType:UIButtonTypeCustom];
    self.toRegBT.frame = CGRectMake(bottomL.frame.origin.x + bottomL.frame.size.width - 3, bottomL.frame.origin.y, 40, bottomL.frame.size.height);
    [_toRegBT setTitle:@"注册" forState:UIControlStateNormal];
    [_toRegBT setTitleColor:[UIColor colorWithRed:(44 % 255) / 255.0 green:(97 % 255) / 255.0 blue:(128 % 255) /255.0 alpha:1.0] forState:UIControlStateNormal];
    [_toRegBT addTarget:self action:@selector(loginAction:) forControlEvents:UIControlEventTouchUpInside];
    _toRegBT.titleLabel.font = [UIFont systemFontOfSize:16.5];
    _toRegBT.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    [self addSubview:_toRegBT];
    
//    [line mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.left.equalTo(self).offset(30);
//        make.right.equalTo(self).offset(-30);
//        make.top.equalTo(_loseBT.mas_bottom).insets(UIEdgeInsetsMake(50, 30, 10, 30));
//        make.bottom.equalTo(bottomL.mas_top).insets(UIEdgeInsetsMake(50, 30, 10, 30));
//        make.height.equalTo(@0.5);
//    }];
//    
//    [bottomL mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.top.equalTo(line.mas_bottom).insets(UIEdgeInsetsMake(10, WIDTH / 2.0 - 60, 0, 0));
//        make.left.equalTo(self).insets(UIEdgeInsetsMake(10, WIDTH / 2.0 - 60, 0, 0));
//        make.bottom.equalTo(self).offset(-20);
//        make.width.equalTo(@85);
//        make.height.equalTo(@40);
//    }];
//    
//    [_toRegBT mas_makeConstraints:^(MASConstraintMaker *make) {
//        
//        make.left.equalTo(bottomL.mas_right).insets(UIEdgeInsetsMake(0, -2, 0, 0));
//        make.centerY.equalTo(bottomL.mas_centerY);
//        make.width.equalTo(@40);
//        make.height.equalTo(bottomL);
//    }];
 
    self.idTF.delegate = self;
    self.passTF.delegate = self;
}

#pragma mark -- ThirdParts登录

//微信
-(void)wechatLogin:(UIButton *)button
{
    [self.myDelegate thirdpartToWechatLogin];
}

//微博
-(void)weiboLogin:(UIButton *)button
{
    [self.myDelegate thirdpartToWeiboLogin];
}

#pragma mark -- 登录
-(void)loginAction:(UIButton *)button
{
    if ([button.currentTitle isEqualToString:@"登  录"]) {
        
        [self.myDelegate toLogin:_idTF.text withPwd:_passTF.text];
        //[self.myDelegate toEmail];
        
    } else if ([button.currentTitle isEqualToString:@"忘记密码"]) {
    
        [self.myDelegate forgetPassword];
        
    } else {
    
        [self.myDelegate toRegister];
    }
}

-(void)backAction:(UIButton *)button
{
    [self.myDelegate toEmail];
}

-(void)backToMainTabVC
{
    [self.myDelegate toEmail];
}

#pragma mark -- textFieldDelegate
-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.frame = CGRectMake(0, -100, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

-(void)textFieldDidEndEditing:(UITextField *)textField
{
    if (_idTF.text.length == 0) {
        
        self.idTF.text = @"用户手机号或邮箱";
    }
    
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
