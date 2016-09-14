//
//  EditUserInfoViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface EditUserInfoViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) UITableView *editUserInfoTV;

@property (nonatomic, strong) NSMutableData *myData;

@end
