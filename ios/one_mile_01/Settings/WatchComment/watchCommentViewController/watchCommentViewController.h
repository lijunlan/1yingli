//
//  watchCommentViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "watchCommentTableViewCell.h"
#import "watchCommentModel.h"

typedef enum
{
    giveComment = 0,
    recevieComment,
    }watchCommentState;

@interface watchCommentViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>
@property (nonatomic,strong) NSMutableArray *giveCommentArray;
@property (nonatomic,strong) NSMutableArray *receiveCommentArray;
@property (nonatomic,strong) UITableView *querryInfoTV;
@property (nonatomic,strong) UITableView *watchCommentTV;
@property (nonatomic,copy) NSString *detailId;
@property (nonatomic,assign)watchCommentState watchCommentS;
@property (nonatomic,strong)NSString *kind;
@property (nonatomic,assign)BOOL isUpLoading;
@property (assign,nonatomic)NSInteger nextPage;//上拉加载

@end
