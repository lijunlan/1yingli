//
//  tutorCommentModel.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface tutorCommentModel : NSObject
@property (nonatomic, strong) NSString *commentId;
@property (nonatomic, strong) NSString *content;
@property (nonatomic, strong) NSString *score;
@property (nonatomic, strong) NSString *createTime;
@property (nonatomic, strong) NSString *nickName;
@property (nonatomic, strong) NSString *iconUrl;
@property (nonatomic, strong) NSString *serviceTitle;
@end
