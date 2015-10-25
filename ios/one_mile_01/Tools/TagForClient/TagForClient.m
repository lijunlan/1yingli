//
//  TagForClient.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "TagForClient.h"

@implementation TagForClient

+ (instancetype) shareTagDataHandle
{
    static TagForClient *tag;
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        
        tag = [[TagForClient alloc] init];
        tag.isTeacher = NO;
        tag.isAlert = NO;
    });
    
    return tag;
}

@end
