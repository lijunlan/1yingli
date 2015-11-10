//
//  UserDefaultDictionary.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/6.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "UserDefaultDictionary.h"

@implementation UserDefaultDictionary

+(void)setUserInfoDic:(NSString *)userID withNumber:(NSString *)number withNickname:(NSString *)nickName withTeacherID:(NSString *)teacherID withIconUrl:(NSString *)iconUrl
{
    [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithObjectsAndKeys:userID, @"uid", number, @"number", nickName, @"nickName", teacherID, @"teacherID", iconUrl, @"iconUrl", nil] forKey:@"userInfo"];
    
    NSLog(@"NSUserDefaults = %@", [[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"]);
}

@end
