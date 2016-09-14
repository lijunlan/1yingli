//
//  watchCommentModel.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/10.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface watchCommentModel : NSObject
@property (nonatomic ,copy)NSString *commentId;
@property (nonatomic ,copy)NSString *content;
@property (nonatomic ,copy)NSString *createTime;
@property (nonatomic ,copy)NSString *name;
@property (nonatomic ,copy)NSString *score;
@property (nonatomic ,copy)NSString *teacherId;
@property (nonatomic ,copy)NSString *url;
@end
