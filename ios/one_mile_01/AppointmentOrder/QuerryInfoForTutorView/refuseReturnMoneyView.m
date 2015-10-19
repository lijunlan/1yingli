//
//  refuseReturnMoneyView.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "refuseReturnMoneyView.h"

@implementation refuseReturnMoneyView

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
    
    
    UILabel *refuseTitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(5, 40, settingView.frame.size.width - 10, 30)];
    refuseTitleLabel.text = @"您确定拒绝退款给学员吗？";
    refuseTitleLabel.textAlignment = NSTextAlignmentCenter;
    refuseTitleLabel.font = [UIFont systemFontOfSize:16];
    [settingView addSubview:refuseTitleLabel];
    
    UILabel *refuseContentLabel = [[UILabel alloc]initWithFrame:CGRectMake(5, 80, settingView.frame.size.width - 10, 60)];
    refuseContentLabel.text = @"如果您拒绝退款将有客服介入处理";
    refuseContentLabel.numberOfLines = 2;
    refuseContentLabel.textAlignment = NSTextAlignmentCenter;
    refuseContentLabel.font = [UIFont systemFontOfSize:16];
    [settingView addSubview:refuseContentLabel];
    
    
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
    [settingView addSubview:cancelButton];
    
    
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
    [settingView addSubview:agreeButton];
    
    
}

//取消
-(void)cancelButtonAction{

    [self.mydelegate cancelRefuseReturnMoney];
}


//同意
-(void)agreeButtonAction{
    
    [self.mydelegate cancelRefuseReturnMoney];
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTutorOrderAction:@"disagreeOrder" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withTeacherID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"] withOrderID:self.refuseRetrunMoneyID] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self showAlert:@"拒绝退款成功"];
        } else {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]) {
                
                [self showAlert:@"该单已拒绝退款"];
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
