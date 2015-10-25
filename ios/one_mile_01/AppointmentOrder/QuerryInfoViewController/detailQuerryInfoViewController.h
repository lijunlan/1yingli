//
//  detailQuerryInfoViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "cancelConsultView.h"
#import "inspectConsultDetailViewController.h"
#import "ProductOrderModel.h"

@protocol detailQuerryInfoInterface <NSObject>

@optional
-(void)toRefreshQuerryInfoView;

@end

@interface detailQuerryInfoViewController : UIViewController

@property (nonatomic, assign)NSInteger tagforColor;
@property (nonatomic, strong)cancelConsultView *cancelConsultV;

@property (nonatomic, strong) ProductOrderModel *orderForDetailM;

@property (nonatomic, assign) id<detailQuerryInfoInterface>myDelegate;

@end
