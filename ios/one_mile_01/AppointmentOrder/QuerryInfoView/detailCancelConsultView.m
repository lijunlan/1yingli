//
//  detailCancelConsultView.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "detailCancelConsultView.h"

@implementation detailCancelConsultView
-(instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        
        [self creatdetailCancelConsultSubView];
    }
    return  self;
}

-(void)creatdetailCancelConsultSubView{
    
    UIView *settingView = [[UIView alloc] initWithFrame:CGRectMake(50, 150, WIDTH - 100, 200)];
    settingView.layer.masksToBounds = YES;
    settingView.layer.cornerRadius = 15.0f;
    settingView.backgroundColor = [UIColor whiteColor];
    [self addSubview:settingView];
    
    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(settingView.frame.size.width / 2 - 50, 50, 100, 100)];
    label.backgroundColor = [UIColor whiteColor];
    label.layer.borderWidth = 2;
    label.layer.masksToBounds = YES;
    label.layer.cornerRadius = 50;
    label.text = @"已取消";
    label.textAlignment = NSTextAlignmentCenter;
    label.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    label.font = [UIFont systemFontOfSize:22];
    label.layer.borderColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0].CGColor;
    [settingView addSubview:label];
    
    UILabel *contentLbel = [[UILabel alloc]initWithFrame:CGRectMake(10, settingView.frame.size.height - 50 , settingView.frame.size.width - 20, 30)];
    contentLbel.textAlignment = NSTextAlignmentCenter;
    contentLbel.font = [UIFont systemFontOfSize:13];
    contentLbel.text = @"您的咨询费用将立刻退回到您的支付宝账户";
    [settingView addSubview:contentLbel];
    
}
@end
