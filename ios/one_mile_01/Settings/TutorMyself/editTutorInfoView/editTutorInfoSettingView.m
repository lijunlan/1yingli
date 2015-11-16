//
//  editTutorInfoSettingView.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/15.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "editTutorInfoSettingView.h"

@interface editTutorInfoSettingView ()<UIAlertViewDelegate>

@end

@implementation editTutorInfoSettingView

-(instancetype)initWithFrame:(CGRect)frame{
    
    if (self = [super initWithFrame:frame]) {
       
        [self creatEditTutorSettingSubView];
        self.isFirstTouch = YES;
    }
    return  self;
}

-(void)creatEditTutorSettingSubView{
    
    UIView *settingView = [[UIView alloc] initWithFrame:CGRectMake(20, 150, WIDTH - 40, 250)];
    settingView.layer.masksToBounds = YES;
    settingView.layer.cornerRadius = 15.0f;
    settingView.backgroundColor = [UIColor whiteColor];
    [self addSubview:settingView];
    
    UIImageView *consultImageV = [[UIImageView alloc]initWithFrame:CGRectMake(10,50,35, 40)];
    consultImageV.image = [UIImage imageNamed:@"consult.png"];
    [settingView addSubview:consultImageV];
    
    UILabel *consultLable = [[UILabel alloc]initWithFrame:CGRectMake(consultImageV.frame.origin.x + consultImageV.frame.size.width + 15,consultImageV.frame.origin.y, 80, 30)];
    consultLable.text = @"咨询方式";
    [settingView addSubview:consultLable];
    
    self.weinxinButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.weinxinButton.frame = CGRectMake(consultLable.frame.origin.x + consultLable.frame.size.width + 15, consultLable.frame.origin.y, 30, 35);
    [self.weinxinButton setImage:[UIImage imageNamed:@"consult_chat_unselected.png"] forState:UIControlStateNormal];
    
    [self.weinxinButton addTarget:self action:@selector(weixinButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [settingView addSubview:self.weinxinButton];
    
    self.SButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.SButton.frame = CGRectMake(self.weinxinButton.frame.origin.x + self.weinxinButton.frame.size.width + 10, self.weinxinButton.frame.origin.y, self.weinxinButton.frame.size.width, self.weinxinButton.frame.size.height);
    [self.SButton setImage:[UIImage imageNamed:@"consult_s_unselected.png"] forState:UIControlStateNormal];
    [self.SButton addTarget:self action:@selector(SButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [settingView addSubview:self.SButton];
    
    self.phoneButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.phoneButton.frame = CGRectMake(self.SButton.frame.size.width + self.SButton.frame.origin.x + 10, self.SButton.frame.origin.y, self.SButton.frame.size.width, self.SButton.frame.size.height);
    [self.phoneButton setImage:[UIImage imageNamed:@"consult_tel_unselected.png"] forState:UIControlStateNormal];
    [self.phoneButton addTarget:self action:@selector(phoneButton:) forControlEvents:UIControlEventTouchUpInside];
    [settingView addSubview:self.phoneButton];
    
    
    UIImageView *adressImageV = [[UIImageView alloc]initWithFrame:CGRectMake(consultImageV.frame.origin.x,consultImageV.frame.origin.y + consultImageV.frame.size.height + 10,40, 35)];
    adressImageV.image = [UIImage imageNamed:@"currentAddress.png"];
    [settingView addSubview:adressImageV];
    
    UILabel *adressLable = [[UILabel alloc]initWithFrame:CGRectMake(adressImageV.frame.origin.x + adressImageV.frame.size.width + 15, adressImageV.frame.origin.y, consultLable.frame.size.width, consultLable.frame.size.height)];
    adressLable.text = @"常驻区域";
    [settingView addSubview:adressLable];

    self.addressText = [[UITextField alloc]initWithFrame:CGRectMake(adressLable.frame.origin.x + adressLable.frame.size.width , adressLable.frame.origin.y, settingView.frame.size.width - adressLable.frame.size.width - adressLable.frame.origin.x - 10 , 30)];
    self.addressText.backgroundColor = [UIColor clearColor];
    self.addressText.layer.borderWidth = 0.5;
    self.addressText.layer.borderColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1].CGColor;
    [settingView addSubview:self.addressText];
    
    
    UIImageView *timeImageV = [[UIImageView alloc]initWithFrame:CGRectMake(adressImageV.frame.origin.x,adressImageV.frame.origin.y + adressImageV.frame.size.height + 10,40, 38)];
    timeImageV.image = [UIImage imageNamed:@"timeDuring.png"];
    [settingView addSubview:timeImageV];
    
    UILabel *timeLable = [[UILabel alloc]initWithFrame:CGRectMake(timeImageV.frame.origin.x + timeImageV.frame.size.width + 10, timeImageV.frame.origin.y, consultLable.frame.size.width + 60, consultLable.frame.size.height)];
    timeLable.text = @"本周可预约次数：";
    [settingView addSubview:timeLable];

    self.timeText = [[UITextField alloc]initWithFrame:CGRectMake(timeLable.frame.origin.x + timeLable.frame.size.width, timeLable.frame.origin.y, 40, 30)];
    self.timeText.backgroundColor = [UIColor clearColor];
    self.timeText.layer.borderWidth = 0.5;
    self.timeText.layer.borderColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1].CGColor;
    [settingView addSubview:self.timeText];
    
    UILabel *timeL = [[UILabel alloc]initWithFrame:CGRectMake(self.timeText.frame.origin.x + self.timeText.frame.size.width + 5, self.timeText.frame.origin.y, 30, 30)];
    timeL.text = @"次";
    [settingView addSubview: timeL];
    
    
    UIButton *cancelButton = [UIButton buttonWithType:UIButtonTypeCustom];
    cancelButton.frame = CGRectMake(settingView.frame.size.width / 5 , timeLable.frame.origin.y + timeLable.frame.size.height + 30, settingView.frame.size.width / 5, 30);
    cancelButton.backgroundColor = [UIColor colorWithRed:154 / 255.0 green:155 / 255.0 blue:156 / 255.0 alpha:1];
    [cancelButton setTitle:@"取消" forState:UIControlStateNormal];
    [cancelButton addTarget:self action:@selector(cancelButtonAction) forControlEvents:UIControlEventTouchUpInside];
    [settingView addSubview:cancelButton];
    
    
    UIButton *editorVSubmitButton = [UIButton buttonWithType:UIButtonTypeCustom];
    editorVSubmitButton.frame = CGRectMake(cancelButton.frame.origin.x + cancelButton.frame.size.width * 2, timeLable.frame.origin.y + timeLable.frame.size.height + 30, cancelButton.frame.size.width, 30);
    editorVSubmitButton.backgroundColor = [UIColor colorWithRed:100 / 255.0 green:200 / 255.0 blue:240 / 255.0 alpha:1];
    [editorVSubmitButton setTitle:@"提交" forState:UIControlStateNormal];
    [settingView addSubview:editorVSubmitButton];
    
    [editorVSubmitButton addTarget:self action:@selector(editSubmitAction:) forControlEvents:UIControlEventTouchUpInside];

}

-(void)setEditTutorM:(editTutorInfoModel *)editTutorM{
    
    self.addressText.placeholder = editTutorM.address;
    self.timeText.placeholder = editTutorM.timeperweek;

}


-(void)cancelButtonAction{

    [self.myDelegate cancelEditTutorInfoButton];
}

-(void)editSubmitAction:(UIButton *)button
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"确定要修改吗？" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    [self addSubview:alert];
    
    [alert show];
}

-(void)weixinButtonAction:(UIButton *)button{
    if (self.isFirstTouch == NO) {
        [self.weinxinButton setImage:[UIImage imageNamed:@"consult_chat_unselected.png"] forState:UIControlStateNormal];
        self.isFirstTouch = YES;
    }else{
      [self.weinxinButton setImage:[UIImage imageNamed:@"consult_chat_selected.png"] forState:UIControlStateNormal];
        self.isFirstTouch = NO;
    }
    
}

-(void)SButtonAction:(UIButton *)button{
    if (self.isSButtonFirstTouch == NO) {
        [button setImage:[UIImage imageNamed:@"consult_s_selected.png"] forState:UIControlStateNormal];
        self.isSButtonFirstTouch = YES;
    }else{
        [button setImage:[UIImage imageNamed:@"consult_s_unselected.png"] forState:UIControlStateNormal];
        self.isSButtonFirstTouch = NO;

    }
}
-(void)phoneButton:(UIButton *)button{
    if (self.isPhoneFirstTouch == NO) {
        [button setImage:[UIImage imageNamed:@"consult_tel_selected.png"] forState:UIControlStateNormal];
        self.isPhoneFirstTouch = YES;
    }else{
        [button setImage:[UIImage imageNamed:@"consult_tel_unselected.png"] forState:UIControlStateNormal];
        self.isPhoneFirstTouch = NO;
    }
}
    
-(void)changeTutorInfo
{
    NSString *talkwayStr = @"[";
    if (!_isFirstTouch) {
        talkwayStr = [talkwayStr stringByAppendingFormat:@"11,"];
    } else {
        
        talkwayStr = [talkwayStr stringByAppendingString:@"10,"];
    }
    
    if (!_isSButtonFirstTouch) {
        talkwayStr = [talkwayStr stringByAppendingString:@"20,"];
    } else {
        talkwayStr = [talkwayStr stringByAppendingString:@"22,"];
    }
    
    if (!_isPhoneFirstTouch) {
        talkwayStr = [talkwayStr stringByAppendingString:@"30]"];
    } else {
        talkwayStr = [talkwayStr stringByAppendingString:@"33]"];
    }
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForChangeTeaInfo:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withTeacherID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"] withperWeek:_timeText.text withPrice:@"0.01" withTalkway:talkwayStr withTime:@"22" withAddress:_addressText.text] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self showAlert:@"修改成功O(∩_∩)O"];
        } else {
        
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
                [alert show];
            }
        }
    }];
}

#pragma mark -- alertviewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    [self endEditing:YES];
    [UIView animateWithDuration:0.5f animations:^{
        
        self.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
        if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
            
            if (buttonIndex == 1) {
                
                [self.myDelegate timeoutToLogin];
            }
        }
        if ([alertView.message isEqualToString:@"确定要修改吗？"]) {
            
            if (buttonIndex == 1) {
                
                [self changeTutorInfo];
            }
        }
    }];
}

#pragma mark -- 警示框回弹自动
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
