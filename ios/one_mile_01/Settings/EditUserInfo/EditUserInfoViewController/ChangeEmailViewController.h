//
//  ChangeEmailViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/7.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol changeEmailInterface <NSObject>

@optional
-(void)changeEmailSuccess:(NSString *)email;

@end

@interface ChangeEmailViewController : UIViewController

@property (nonatomic, strong) UITextField *emailTF;
@property (nonatomic, strong) UITextField *emCheckNoTF;

@property (nonatomic, strong) NSString *userIDforChangeEm;

@property (nonatomic, assign) id<changeEmailInterface>myDelegate;

@end
