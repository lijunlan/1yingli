//
//  LoginViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LoginView.h"
#import "RegisterViewController.h"
#import "forgetPasswordViewController.h"
#import "LoginModal.h"
#import "WeiboSDK.h"

//collection
@protocol lcForCollectionInterface <NSObject>

@optional
-(void)transitionViewToCollection:(LoginModal *)users;

@end

//email
@protocol lcForEmailInterface<NSObject>

@optional
-(void)transitionVIewToEmail:(LoginModal *)users;

@end

//order
@protocol lcForOrderInterface<NSObject>

@optional
-(void)transitionViewToOrder:(LoginModal *)users;

@end

//setting
@protocol lcForSettingInterface <NSObject>

@optional
-(void)transitionViewToSetting:(LoginModal *)users;

@end

@interface LoginViewController : UIViewController<loginInterface, regControllerInterface, WeiboSDKDelegate>

@property (nonatomic, assign) id<lcForCollectionInterface>myColDelegate;
@property (nonatomic, assign) id<lcForEmailInterface>myEmailDelegate;
@property (nonatomic, assign) id<lcForOrderInterface>myOrderDelegate;

+ (instancetype) shareLoginHandler;

- (void) weiboHandleURL:(NSURL *)url;

@end
