//
//  cancelConsultView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "detailCancelConsultView.h"
#import "ProductOrderModel.h"

@protocol cancelConsultInterface <NSObject>

@optional
-(void)popFromCancelConsult;

@end

@interface cancelConsultView : UIView

@property (nonatomic, strong) detailCancelConsultView *detailCancelConsultV;
@property (nonatomic, strong) ProductOrderModel *cancelOrderM;

@property (nonatomic, assign) id<cancelConsultInterface>myDelegate;

@end
