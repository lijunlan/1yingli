//
//  QuerryInfoView.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/26.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UserInfoModel.h"

@protocol querryEvent <NSObject>

@optional
-(void)cancelToBack;
-(void)querryToPay:(NSString *)clientName withPhone:(NSString *)phone withChat:(NSString *)chat withEmail:(NSString *)email;

@end

@interface QuerryInfoView : UIView<UITextFieldDelegate>

@property (nonatomic, strong) UITextField *clientTF;
@property (nonatomic, strong) UITextField *phoneNumTF;
@property (nonatomic, strong) UITextField *chatTF;
@property (nonatomic, strong) UITextField *emailTF;

@property (nonatomic, strong) UserInfoModel *userQueryM;

@property (nonatomic, assign) id<querryEvent>myDelegate;

@end
