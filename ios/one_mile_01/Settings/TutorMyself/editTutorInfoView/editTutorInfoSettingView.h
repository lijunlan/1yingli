//
//  editTutorInfoSettingView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/15.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "editTutorInfoModel.h"

@protocol cancelEditTutor <NSObject>

@optional
-(void)querryEditTutorInfoButton:(NSString *)time withTalkway:(NSString *)talkway withPrice:(NSString *)price withAddress:(NSString *)address;
-(void)cancelEditTutorInfoButton;
-(void)timeoutToLogin;

@end

@interface editTutorInfoSettingView : UIView
@property(nonatomic, strong)UIButton *weinxinButton;
@property(nonatomic, strong)UIButton *SButton;
@property(nonatomic, strong)UIButton *phoneButton;
@property(nonatomic, strong)UITextField *addressText;
@property(nonatomic, strong)UITextField *timeText;
@property(nonatomic, assign)BOOL isFirstTouch;
@property(nonatomic, assign)BOOL isSButtonFirstTouch;
@property(nonatomic, assign)BOOL isPhoneFirstTouch;
@property(nonatomic, assign)editTutorInfoModel *editTutorM;

@property (nonatomic, assign) id<cancelEditTutor>myDelegate;

@end
