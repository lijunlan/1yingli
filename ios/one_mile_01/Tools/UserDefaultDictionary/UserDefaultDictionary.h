//
//  UserDefaultDictionary.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/6.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UserDefaultDictionary : NSObject

+ (void) setUserInfoDic:(NSString *)userID withNumber:(NSString *)number withNickname:(NSString *)nickName withTeacherID:(NSString *)teacherID withIconUrl:(NSString *)iconUrl;

@end
