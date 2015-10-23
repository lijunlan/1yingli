//
//  OrderViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "QuerryInfoViewController.h"
#import "OrderUnloginViewController.h"
#import "tutorQueeryInfoViewController.h"
#import "TagForClient.h"

@interface OrderViewController : UIViewController
@property (nonatomic,strong)QuerryInfoViewController *QuerryInfoVC;
@property (nonatomic,strong)OrderUnloginViewController *orderUnloginVC;
@property (nonatomic,strong)tutorQueeryInfoViewController *tutorQuerryInfoVC;

@end
