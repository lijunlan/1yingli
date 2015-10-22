//
//  LoginView.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WeiboSDK.h"

@protocol loginInterface <NSObject>

@optional
-(void)toEmail;

-(void)toRegister;

-(void)toLogin:(NSString *)username withPwd:(NSString *)password;

-(void)forgetPassword;

-(void)thirdpartToWechatLogin;

-(void)thirdpartToWeiboLogin;

@end

@interface LoginView : UIView

@property (nonatomic, strong) UILabel *idLabel;
@property (nonatomic, strong) UILabel *passLabel;

@property (nonatomic, strong) UITextField *idTF;
@property (nonatomic, strong) UITextField *passTF;

@property (nonatomic, assign) id<loginInterface> myDelegate;

-(void)backToMainTabVC;

@end
