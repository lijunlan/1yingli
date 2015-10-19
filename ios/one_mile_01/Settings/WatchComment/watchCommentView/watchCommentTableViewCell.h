//
//  watchCommentTableViewCell.h
//  one_mile_01
//
//  Created by 王进帅 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "watchCommentModel.h"
@interface watchCommentTableViewCell : UITableViewCell
@property (nonatomic,strong)UILabel *titileLabel;
@property (nonatomic,strong)UILabel *contentLabel;
@property (nonatomic,strong)UILabel *lineLabel;
@property (nonatomic,strong)UILabel *nameLabel;
@property (nonatomic,strong)UILabel *timeLabel;
@property (nonatomic,strong)UIImageView *headImageView;
@property (nonatomic,strong)UIButton *startButton;
@property (nonatomic,copy)NSString *watchCommentScore;
@property (nonatomic,strong)watchCommentModel *watchCommentM;
@property (nonatomic,assign)NSInteger score;
@property (nonatomic,strong)UIImageView *bgImageView;
@end
