//
//  judgementView.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ProductOrderModel.h"

@protocol judgementInterface <NSObject>

@optional
-(void)alertToLogin:(NSInteger)btIndex;

@end

@interface judgementView : UIView

@property (nonatomic, strong) ProductOrderModel *orderForJudgeM;

@property (nonatomic, assign) id<judgementInterface>myDelegate;

@end
