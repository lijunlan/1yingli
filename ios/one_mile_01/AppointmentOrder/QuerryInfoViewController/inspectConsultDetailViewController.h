//
//  inspectConsultDetailViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ProductOrderModel.h"

@interface inspectConsultDetailViewController : UIViewController<UIScrollViewDelegate>

@property(nonatomic,strong)UILabel *appointmentTitle ;
@property(nonatomic,strong)UILabel *moneyLabel;//元
@property(nonatomic,strong)UILabel *hourLabel;//小时
@property(nonatomic,strong)UIScrollView *scrollView;//滚动
@property(nonatomic,strong)UILabel *phoneNum;
@property(nonatomic,strong)UILabel *weixinNum;
@property(nonatomic,strong)UILabel *emailNum;

@property (nonatomic, strong) ProductOrderModel *orderForInspect;

@end
