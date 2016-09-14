//
//  tutorCancelApionmentView.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/18.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorCancelApionmentView.h"

@implementation tutorCancelApionmentView

-(instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        
        [self creatcancelConsultSubView];
    }
    return  self;
}
-(void)creatcancelConsultSubView{
    
    UIView *settingView = [[UIView alloc] initWithFrame:CGRectMake(50, 150, WIDTH - 100, 200)];
    settingView.layer.masksToBounds = YES;
    settingView.layer.cornerRadius = 15.0f;
    settingView.backgroundColor = [UIColor whiteColor];
    [self addSubview:settingView];
    
    
    UILabel *cancelLabel = [[UILabel alloc]initWithFrame:CGRectMake(5, 10, settingView.frame.size.width - 20, 60)];
    cancelLabel.text = @"您确定取消本次预约吗？请说明理由";
    cancelLabel.textAlignment = NSTextAlignmentCenter;
    cancelLabel.font = [UIFont systemFontOfSize:12];
    [settingView addSubview:cancelLabel];
    
    self.textView = [[UITextView alloc]initWithFrame:CGRectMake(10, cancelLabel.frame.origin.y + cancelLabel.frame.size.height + 5, settingView.frame.size.width - 20, 120)];
    self.textView.layer.borderWidth = 0.5;
    self.textView.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.textView.layer.masksToBounds = YES;
    self.textView.layer.cornerRadius = 15;
    [settingView addSubview:self.textView];
    
    //    取消
    UIButton *cancelButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [settingView addSubview:cancelButton];
    [cancelButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(settingView.mas_bottom).offset(-20);
        make.left.equalTo(settingView.mas_left).offset(15);
        make.right.equalTo(settingView.mas_right).offset(-(settingView.frame.size.width - 15 - 90));
        make.top.equalTo(settingView.mas_top).offset(settingView.frame.size.height - 30 - 20);
    }];
    cancelButton.backgroundColor = [UIColor lightGrayColor];
    cancelButton.layer.masksToBounds = YES;
    cancelButton.layer.cornerRadius = 15;
    [cancelButton setTitle:@"取消" forState:UIControlStateNormal];
    [cancelButton addTarget:self action:@selector(cancelButtonAction) forControlEvents:UIControlEventTouchUpInside];
    
    
    //    确定
    UIButton *agreeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [settingView addSubview:agreeButton];
    [agreeButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(settingView.mas_bottom).offset(-20);
        make.left.equalTo(settingView.mas_left).offset(settingView.frame.size.width - 15 - 90);
        make.right.equalTo(settingView.mas_right).offset(- 15);
        make.top.equalTo(settingView.mas_top).offset(settingView.frame.size.height - 30 - 20);
    }];
    agreeButton.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    agreeButton.layer.masksToBounds = YES;
    agreeButton.layer.cornerRadius = 15;
    [agreeButton setTitle:@"确认" forState:UIControlStateNormal];
    [agreeButton addTarget:self action:@selector(agreeButtonAction) forControlEvents:UIControlEventTouchUpInside];
}

//取消
-(void)cancelButtonAction{
    
    [self.mydelegate tutorCancelAppionmentMethod];
    
}


//同意
-(void)agreeButtonAction{
    
  [self.mydelegate tutorCancelAppionmentMethod];

    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTutorRefuse:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withTeacherID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"] withOrderID:self.tutorCancelApionmentOrderID withrefuseReason:self.textView.text] connectBlock:^(id data) {
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self showAlert:@"取消咨询成功"];
        } else {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]) {
                
                [self showAlert:@"该单已取消咨询"];
            } else if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                [self showAlert:@"亲，登录超时请重新登录吧"];
            }
        }
    }];
    
}
//自动警示框回弹
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


@end
