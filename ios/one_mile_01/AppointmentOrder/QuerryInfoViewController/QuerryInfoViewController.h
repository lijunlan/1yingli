//
//  QuerryInfoViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/26.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "detailQuerryInfoViewController.h"
typedef enum
{
    OrderStateForNoPay = 0,
    OrderStateForWaitEnsure,
    OrderStateForEnsured,
    OrderStateForEnsureTime,
    OrderStateForServing,
    OrderStateForComments,
    OrderStateForEnd
}OrderState;

@interface QuerryInfoViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) UITableView *querryInfoTV;

@property (nonatomic, assign) OrderState stateForOrder;
@property (nonatomic, assign) NSInteger tagColor;

@property (nonatomic, copy) NSString *contactWayForQuerry;

@end
