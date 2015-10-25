//
//  TagForClient.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LoginView.h"

@interface TagForClient : NSObject

+ (instancetype) shareTagDataHandle;

@property (nonatomic, assign) BOOL isTeacher;   //标志是否是导师
@property (nonatomic, strong) LoginView *weiboLoginV; //微博登录时，模态问题

@property (nonatomic, assign) BOOL isAlert; //是否提示登录超时

@end
