//
//  EmailViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EmailTableViewCell.h"
#import "EmailSecondPageViewController.h"
#import "EmailFirstViewController.h"
@interface EmailViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, retain) UITableView *myTableView;

@end
