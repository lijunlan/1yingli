//
//  tutoreBaluationView.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutoreBaluationView.h"

@implementation tutoreBaluationView
-(instancetype)initWithFrame:(CGRect)frame{
    
    if (self = [super initWithFrame:frame]) {
        self.buttonArray = [NSMutableArray array];
        self.starNum = 1;
        [self creatcancelConsultSubView];
    }
    return  self;
}
-(void)creatcancelConsultSubView{
    
    self.settingView = [[UIView alloc] initWithFrame:CGRectMake(50, 150, WIDTH - 100, 200)];
    self.settingView.layer.masksToBounds = YES;
    self.settingView.layer.cornerRadius = 15.0f;
    self.settingView.backgroundColor = [UIColor whiteColor];
    [self addSubview:self.settingView];

    UILabel *tutorBaluationTitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 10, self.settingView.frame.size.width - 20, 25)];
    tutorBaluationTitleLabel.text = @"评价学员";
    tutorBaluationTitleLabel.textAlignment = NSTextAlignmentCenter;
    tutorBaluationTitleLabel.font = [UIFont systemFontOfSize:15];
    [self.settingView addSubview:tutorBaluationTitleLabel];
    
    self.textView = [[UITextView alloc]initWithFrame:CGRectMake(10, tutorBaluationTitleLabel.frame.origin.y + tutorBaluationTitleLabel.frame.size.height + 5, self.settingView.frame.size.width - 20, 90)];
    self.textView.layer.borderWidth = 0.5;
    self.textView.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.textView.layer.masksToBounds = YES;
    self.textView.layer.cornerRadius = 15;
    [self.settingView addSubview:self.textView];
    
    for (int i = 0; i < 5; i++) {
        
        self.baluationStarButton = [UIButton buttonWithType:UIButtonTypeCustom];
        
        if (i < 2) {
            
            self.baluationStarButton.frame = CGRectMake(self.settingView.frame.size.width / 2.0 - 30 * (2 - i) - 2 * (2 - i) - 15, _textView.frame.origin.y + _textView.frame.size.height + 5, 25, 25);
        } else if (i == 2) {
            
            self.baluationStarButton.frame = CGRectMake(self.settingView.frame.size.width / 2.0 - 15, _textView.frame.origin.y + _textView.frame.size.height + 5, 25, 25);
        } else {
            
            self.baluationStarButton.frame = CGRectMake(self.settingView.frame.size.width / 2.0 + 30 * (i - 3) + 2 * (i - 2) + 15, _textView.frame.origin.y + _textView.frame.size.height + 5, 25, 25);
        }
        self.baluationStarButton.tag = 10160 + i;
        [self.baluationStarButton setBackgroundImage:[UIImage imageNamed:@"comments_dislike.png"] forState:UIControlStateNormal];
        [self.settingView addSubview:self.baluationStarButton];
        
        [self.baluationStarButton addTarget:self action:@selector(baluationStarButtonAction:) forControlEvents:UIControlEventTouchUpInside];
        
        [self.buttonArray addObject:self.baluationStarButton];
    }
    
    //    取消
    UIButton *cancelButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [_settingView addSubview:cancelButton];
    [cancelButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(_settingView.mas_bottom).offset(-5);
        make.left.equalTo(_settingView.mas_left).offset(15);
        make.right.equalTo(_settingView.mas_right).offset(-(_settingView.frame.size.width - 15 - 90));
        make.top.equalTo(_settingView.mas_top).offset(_settingView.frame.size.height - 30 - 5);
    }];
    cancelButton.backgroundColor = [UIColor lightGrayColor];
    cancelButton.layer.masksToBounds = YES;
    cancelButton.layer.cornerRadius = 15;
    [cancelButton setTitle:@"取消" forState:UIControlStateNormal];
    [cancelButton addTarget:self action:@selector(cancelButtonAction) forControlEvents:UIControlEventTouchUpInside];
    
    
    //    确定
    UIButton *agreeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.settingView addSubview:agreeButton];
    [agreeButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.settingView.mas_bottom).offset(-5);
        make.left.equalTo(_settingView.mas_left).offset(_settingView.frame.size.width - 15 - 90);
        make.right.equalTo(_settingView.mas_right).offset(- 15);
        make.top.equalTo(_settingView.mas_top).offset(_settingView.frame.size.height - 30 - 5);
    }];

    agreeButton.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    agreeButton.layer.masksToBounds = YES;
    agreeButton.layer.cornerRadius = 15;
    [agreeButton setTitle:@"确认" forState:UIControlStateNormal];
    [agreeButton addTarget:self action:@selector(agreeButtonAction) forControlEvents:UIControlEventTouchUpInside];
    [self.settingView addSubview:agreeButton];

}
//点亮小星星
-(void)baluationStarButtonAction:(UIButton *)button{
    
    self.starNum = 0;
    
    for (UIButton *tmpBT in self.buttonArray) {
        
        if (tmpBT.tag == button.tag) {
            
            break;
        }
        
        self.starNum ++;
    }
    
    for (int j = 0; j < self.buttonArray.count; j++) {
        
        UIButton *bt = [self.buttonArray objectAtIndex:j];
        
        if (j <= self.starNum) {
            
            [bt setBackgroundImage:[UIImage imageNamed:@"comments_favor.png"] forState:UIControlStateNormal];
        } else {
            
            [bt setBackgroundImage:[UIImage imageNamed:@"comments_dislike.png"] forState:UIControlStateNormal];
        }
    }
}

//取消
-(void)cancelButtonAction{

    [self.mydelegate cancelTutorBaluation];
}

//确认
-(void)agreeButtonAction{
    [self.mydelegate cancelTutorBaluation];
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForJudgement:@"teacher" withMethod:@"commentUser" withOrderID:self.tutorBaluationOrderID withTeacherID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"] withScore:(self.starNum + 1) withContent:self.textView.text withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"]] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self showAlert:@"评论成功"];
        } else {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]) {
                
                [self showAlert:@"该学员已经评价过了"];
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
