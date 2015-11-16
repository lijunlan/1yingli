//
//  tutorQueeryInfoViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "tutorQuerryInfoTableViewCell.h"
#import "TutordetailQuerryInfoViewController.h"

typedef enum
{
    TutorOrderStateForWaitEnsure = 0,
    TutorOrderStateForEnsured,
    TutorOrderStateForEnsureTime,
    TutorOrderStateForServing,
    TutorOrderStateForComments,
    TutorOrderStateForEnd
}TutorOrderState;

@interface tutorQueeryInfoViewController : UIViewController
@property (nonatomic, assign)TutorOrderState TutorstateForOrder;
@property (nonatomic, strong) UITableView *tutorQuerryInfoTableV;
@property(nonatomic,assign)BOOL isUpLoading;
@property (assign,nonatomic)NSInteger nextPage;//上拉加载

@end
