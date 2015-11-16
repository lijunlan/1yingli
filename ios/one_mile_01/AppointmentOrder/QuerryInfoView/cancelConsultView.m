//
//  cancelConsultView.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "cancelConsultView.h"

@implementation cancelConsultView

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
  
    
    UILabel *cancelLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, 40, settingView.frame.size.width - 20, 30)];
    cancelLabel.text = @"确定取消咨询吗？";
    cancelLabel.textAlignment = NSTextAlignmentCenter;
    cancelLabel.font = [UIFont systemFontOfSize:20];
    [settingView addSubview:cancelLabel];
    
    
    UIButton *cancelConsultButton = [[UIButton alloc]initWithFrame:CGRectMake(100,cancelLabel.frame.origin.y + cancelLabel.frame.size.height + 30, settingView.frame.size.width - 200, 35)];
    cancelConsultButton.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    cancelConsultButton.layer.masksToBounds = YES;
    cancelConsultButton.layer.cornerRadius = 17;
    [cancelConsultButton setTitle:@"确认" forState:UIControlStateNormal];
    [cancelConsultButton addTarget:self action:@selector(cancelConsultButtonAction) forControlEvents:UIControlEventTouchUpInside];
    [settingView addSubview:cancelConsultButton];
}

//动画弹窗  撤回原来弹窗

-(void)cancelConsultButtonAction{
    
    [self CancelConsult];
    
    [self.myDelegate popFromCancelConsult];
}

- (void)CancelConsult{//时间
    
    [NSTimer scheduledTimerWithTimeInterval:1.0f
                                     target:self
                                   selector:@selector(cancelConsultMethod)
                                   userInfo:nil
                                    repeats:YES];
}


//回收弹窗
-(void)cancelConsultMethod
{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
        
    }];
}

@end
