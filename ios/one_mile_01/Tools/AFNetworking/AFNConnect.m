//
//  AFNConnect.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/28.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "AFNConnect.h"
#import "JSONKit.h"
#import "RSA/RSA.h"

@implementation AFNConnect

#pragma mark -- 登陆
+(NSData *)createDataForLogin:(NSString *)username withPwd:(NSString *)password
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    NSLog(@"userPath = %@", userPath);
    
    BOOL isUsers = [[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isUsers) {
        NSLog(@"login1 is failing");
    }
    
    NSString *loginPath = [userPath stringByAppendingPathComponent:@"login.txt"];
    
    //加密
    NSString *passwordEnc = [RSA encryptString:password publicKey:RSA_KEY];
    
    NSString *loginStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"iosLogin\",\"username\": \"%@\",\"password\": \"%@\"}", username, passwordEnc];
    
    BOOL loginResult = [loginStr writeToFile:loginPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!loginResult) {
        
        NSLog(@"login2 is failing!");
    }
    
    return [NSData dataWithContentsOfFile:loginPath];
}

#pragma mark -- 三方登录
//微信
+(NSData *)createDataForThirdWechatLogin:(NSString *)code
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        NSLog(@"thirdWechatLogin1 is failing");
    }
    
    NSString *loginPath = [userPath stringByAppendingPathComponent:@"thirdLogin.txt"];
    
    NSString *loginStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"loginByWeixin\",\"weixin_code\": \"%@\",\"kind\": \"mobile\"}", code];
    
    if (![loginStr writeToFile:loginPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        
        NSLog(@"thirdWechatLogin2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:loginPath];
}

//微博
+(NSData *)createDataForThirdWeiboLogin:(NSString *)accessToken withUID:(NSString *)uid
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        NSLog(@"thirdWweiboLogin1 is failing");
    }
    
    NSString *loginPath = [userPath stringByAppendingPathComponent:@"thirdLogin.txt"];
    
    NSString *loginStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"loginByWeibo\",\"weibo_accessToken\": \"%@\",\"weibo_uid\": \"%@\"}", accessToken, uid];
    
    if (![loginStr writeToFile:loginPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        
        NSLog(@"thirdWeiboLogin2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:loginPath];
}

#pragma mark -- 获取验证码
+(NSData *)createDataForCheckNo:(NSString *)username
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    NSLog(@"userPath = %@", userPath);
    
    BOOL isCheck = [[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isCheck) {
        NSLog(@"check1 is failing");
    }

    NSString *checkPath = [userPath stringByAppendingPathComponent:@"checkno.txt"];
    
    NSString *checkStr = [NSString stringWithFormat:@"{\"style\": \"function\",\"method\": \"getCheckNo\",\"username\": \"%@\"}", username];
    
    BOOL checkResult = [checkStr writeToFile:checkPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!checkResult) {
        
        NSLog(@"check2 is failing!");
    }
    
    return [NSData dataWithContentsOfFile:checkPath];
}

#pragma mark -- 注册
+(NSData *)createDataForRegister:(NSString *)username withPwd:(NSString *)password withCheckNo:(NSString *)checkNo withNickName:(NSString *)nickName
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    NSLog(@"userPath = %@", userPath);
    
    BOOL isRegister = [[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isRegister) {
        NSLog(@"register1 is failing");
    }

    NSString *registerPath = [userPath stringByAppendingPathComponent:@"register.txt"];
    
    NSString *passworkEnc = [RSA encryptString:password publicKey:RSA_KEY];
    
    NSString *registerStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"iosRegister\",\"username\": \"%@\",\"password\": \"%@\",\"checkNo\": \"%@\",\"nickName\": \"%@\"}",username, passworkEnc, checkNo, nickName];
    
    BOOL registerResult = [registerStr writeToFile:registerPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!registerResult) {
        
        NSLog(@"register2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:registerPath];
}

#pragma mark -- 导师列表
+(NSData *)createDataForTeacherList:(NSString *)currentID withdownUpdata:(BOOL)downUpdata withisFirst:(BOOL)isFirst
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    NSLog(@"document = %@",documentPath);
    
    NSString *teacherPath = [documentPath stringByAppendingPathComponent:@"teacher"];
    NSLog(@"teacherPath = %@", teacherPath);
    
    BOOL isTeacher = [[NSFileManager defaultManager] createDirectoryAtPath:teacherPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isTeacher) {
        NSLog(@"list1 is failing");
    }
    
    NSString *listPath = [teacherPath stringByAppendingPathComponent:@"list.txt"];
    
    NSString *teacherListStr;
    
    if (isFirst) {
        
        isFirst = NO;
        teacherListStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"1\",\"lastTeacherId\": \"max\"}"];
    } else {
        
        if (downUpdata) {
            
            teacherListStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"1\",\"firstTeacherId\": \"%@\"}", currentID];
        } else {
            
            teacherListStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"1\",\"lastTeacherId\": \"%@\"}", currentID];
        }
    }
    
    BOOL listResult = [teacherListStr writeToFile:listPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!listResult) {
        
        NSLog(@"list2 is failing!");
    }
    
    return [NSData dataWithContentsOfFile:listPath];
}

#pragma mark -- 创业4 求职2 校园8 猎奇16
+(NSData *)createDataForList:(NSString *)tip withNextID:(NSString *)currentID withdownUpdata:(BOOL)downUpdata withisFirst:(BOOL)isFirst
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *mainPath;
    NSString *subPath;
    
    if ([tip isEqualToString:@"2"]) {//求职
        
        mainPath = [documentPath stringByAppendingPathComponent:@"apply"];
        //NSLog(@"applyPath = %@", mainPath);
        
        BOOL isMain = [[NSFileManager defaultManager] createDirectoryAtPath:mainPath withIntermediateDirectories:YES attributes:nil error:nil];
        if (!isMain) {
            
            NSLog(@"apply1 is failing");
        }
        
        subPath = [mainPath stringByAppendingPathComponent:@"list.txt"];
        
        NSString *applyStr;
        
        if (isFirst) {
            
            isFirst = NO;
            applyStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"max\"}", tip];
        } else {
        
            if (downUpdata) {
                
                applyStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"firstTeacherId\": \"%@\"}", tip, currentID];
            } else {
            
                applyStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"%@\"}", tip, currentID];
            }
        }
        
        BOOL applyResult = [applyStr writeToFile:subPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
        if (!applyResult) {
            
            NSLog(@"apply2 is failing");
        }
    } else if ([tip isEqualToString:@"4"]) {//创业
    
        mainPath = [documentPath stringByAppendingPathComponent:@"entrepreneurial"];
        NSLog(@"entrepreneurialPath = %@", mainPath);
        
        BOOL isMain = [[NSFileManager defaultManager] createDirectoryAtPath:mainPath withIntermediateDirectories:YES attributes:nil error:nil];
        if (!isMain) {
            
            NSLog(@"entrepreneurial1 is failing");
        }
        
        subPath = [mainPath stringByAppendingPathComponent:@"list.txt"];
        
        NSString *entreStr;
        
        if (isFirst) {
            
            isFirst = NO;
            entreStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"max\"}", tip];
        } else {
        
            if (downUpdata) {
                
                entreStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"firstTeacherId\": \"%@\"}", tip, currentID];
            } else {
            
                entreStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"%@\"}", tip, currentID];
            }
        }
        
        BOOL applyResult = [entreStr writeToFile:subPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
        if (!applyResult) {
            
            NSLog(@"entrepreneurial2 is failing");
        }
    } else if ([tip isEqualToString:@"8"]) {//校园
    
        mainPath = [documentPath stringByAppendingPathComponent:@"campus"];
        NSLog(@"campusPath = %@", mainPath);
        
        BOOL isMain = [[NSFileManager defaultManager] createDirectoryAtPath:mainPath withIntermediateDirectories:YES attributes:nil error:nil];
        if (!isMain) {
            
            NSLog(@"campus1 is failing");
        }
        
        subPath = [mainPath stringByAppendingPathComponent:@"list.txt"];
        
        NSString *campusStr;
        
        if (isFirst) {
            
            isFirst = NO;
            campusStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"max\"}", tip];
        } else {
        
            if (downUpdata) {
                
                campusStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"firstTeacherId\": \"%@\"}", tip, currentID];
            } else {
            
                campusStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"%@\"}", tip, currentID];
            }
        }
        
        BOOL applyResult = [campusStr writeToFile:subPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
        if (!applyResult) {
            
            NSLog(@"campus2 is failing");
        }
    } else {//猎奇
    
        mainPath = [documentPath stringByAppendingPathComponent:@"bodyshots"];
        NSLog(@"bodyshotsPath = %@", mainPath);
        
        BOOL isMain = [[NSFileManager defaultManager] createDirectoryAtPath:mainPath withIntermediateDirectories:YES attributes:nil error:nil];
        if (!isMain) {
            
            NSLog(@"bodyshots1 is failing");
        }
        
        subPath = [mainPath stringByAppendingPathComponent:@"list.txt"];
        
        NSString *strongStr;
        
        if (isFirst) {
            
            isFirst = NO;
            strongStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"max\"}", tip];
        } else {
        
            if (downUpdata) {
                
                strongStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"firstTeacherId\": \"%@\"}", tip, currentID];
            } else {
            
                strongStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\":\"getTeacherSInfoList\",\"tip\": \"%@\",\"lastTeacherId\": \"%@\"}", tip, currentID];
            }
        }
        
        BOOL applyResult = [strongStr writeToFile:subPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
        if (!applyResult) {
            
            NSLog(@"bodyshots2 is failing");
        }
    }
    
    return [NSData dataWithContentsOfFile:subPath];
}

#pragma mark -- 搜索
+(NSData *)createDataForSearchTutor:(NSString *)keyWords andFilter:(NSString *)filter andPage:(NSString *)index
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    NSLog(@"userPath = %@", userPath);
    
    BOOL isUsers = [[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isUsers) {
        
        NSLog(@"search1 is failing");
    }
    
    NSString *searchPath = [userPath stringByAppendingPathComponent:@"search.txt"];
    
    //NSString *encodedKeywords = (NSString *)CFBridgingRelease(CFURLCreateStringByAddingPercentEscapes( kCFAllocatorDefault, (CFStringRef)[NSString stringWithFormat:@"%@", keyWords], NULL, NULL,  kCFStringEncodingUTF8 ));
  
    NSString *searchStr;
    if (filter.length == 0) {
        
        searchStr = [NSString stringWithFormat:@"{\"style\": \"function\",\"method\": \"search\",\"word\": \"%@\",\"page\": \"%@\"}", [keyWords stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding], index];
    } else {
        searchStr = [NSString stringWithFormat:@"{\"style\": \"function\",\"method\": \"search\",\"word\": \"%@\",\"filter\": \"%@\",\"page\": \"%@\"}", [keyWords stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding], filter, index];
    }
    
    BOOL searchResult = [searchStr writeToFile:searchPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!searchResult) {
        
        NSLog(@"search2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:searchPath];
}

#pragma mark -- 通知
+(NSData *)createDataForNotification:(NSString *)userID withPage:(NSString *)pageNum
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    NSLog(@"userPath = %@", userPath);
    
    BOOL isUsers = [[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isUsers) {
        
        NSLog(@"notification1 is failing");
    }

    NSString *notificationPath = [userPath stringByAppendingPathComponent:@"notification.txt"];
    
    NSString *sendNotificationStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"getNotification\",\"uid\": \"%@\",\"page\": \"%@\"}", userID, pageNum];
    
    BOOL sendResult = [sendNotificationStr writeToFile:notificationPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!sendResult) {
        
        NSLog(@"notification2 is failing!");
    }
    
    return [NSData dataWithContentsOfFile:notificationPath];
}

#pragma mark -- 导师详细
+(NSData *)createDataForTeacherDetail:(NSString *)teacherID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *teacherPath = [documentPath stringByAppendingPathComponent:@"teacher"];
    NSLog(@"userPath = %@", teacherPath);
    
    BOOL isTeacher = [[NSFileManager defaultManager] createDirectoryAtPath:teacherPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isTeacher) {
        
        NSLog(@"detail1 is failing");
    }
    
    NSString *detailPath = [teacherPath stringByAppendingPathComponent:@"detail.txt"];
    
    NSString *teacherDetailStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"getTeacherInfo\",\"teacherId\": \"%@\"}", teacherID];
    
    BOOL detailResult = [teacherDetailStr writeToFile:detailPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!detailResult) {
        
        NSLog(@"detail2 is failing!");
    }
    
    return [NSData dataWithContentsOfFile:detailPath];
}

#pragma mark -- 成为导师
+(NSData *)createDataForTobeTutor:(NSString *)userID withApplication:(NSString *)application
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *teacherPath = [documentPath stringByAppendingPathComponent:@"teacher"];
    
    if (![[NSFileManager defaultManager] createDirectoryAtPath:teacherPath withIntermediateDirectories:YES attributes:nil error:nil]) {
        NSLog(@"tobeTutor1 is failing");
    }
    
    NSString *tobeTutorPath = [teacherPath stringByAppendingString:@"tobe.txt"];
    
    NSString *tobeTutorStr = [NSString stringWithFormat:@"{\"style\": \"function\",\"method\": \"createApplication\",\"uid\": \"%@\",\"application\":%@}", userID, application];
    
    if (![tobeTutorStr writeToFile:tobeTutorPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        NSLog(@"tobeTutor2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:tobeTutorPath];
}

#pragma mark -- 评价
//评价导师
+(NSData *)createDataForTeacherCommentList:(NSString *)teacherID withPage:(NSString *)pageNum
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *teacherPath = [documentPath stringByAppendingPathComponent:@"teacher"];
    NSLog(@"teacherPath = %@", teacherPath);
    
    BOOL isTeacher = [[NSFileManager defaultManager] createDirectoryAtPath:teacherPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isTeacher) {
        
        NSLog(@"comments1 is failing");
    }

    NSString *commentsPath = [teacherPath stringByAppendingPathComponent:@"comments.txt"];
    
    NSString *commentStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\": \"getCommentList\",\"teacherId\": \"%@\",\"page\": \"%@\"}", teacherID, pageNum];
    
    BOOL commentResult = [commentStr writeToFile:commentsPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!commentResult) {
        
        NSLog(@"comments2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:commentsPath];
}

//给出的评价
+(NSData *)createDataForStuCommentList:(NSString *)userID withPage:(NSString *)pageNum
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *teacherPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isTeacher = [[NSFileManager defaultManager] createDirectoryAtPath:teacherPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isTeacher) {
        
        NSLog(@"commentsToStu1 is failing");
    }
    
    NSString *commentsPath = [teacherPath stringByAppendingPathComponent:@"comments.txt"];
    
    NSString *commentStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"getCommentList\",\"uid\": \"%@\",\"page\": \"%@\"}", userID, pageNum];
    
    BOOL commentResult = [commentStr writeToFile:commentsPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!commentResult) {
        
        NSLog(@"commentsToStu2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:commentsPath];
}

//给我的评价 & 我给的评价爱
+(NSData *)createDataForStuCommentList:(NSString *)userID withPage:(NSString *)pageNum withKind:(NSString *)kind
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *teacherPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isTeacher = [[NSFileManager defaultManager] createDirectoryAtPath:teacherPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isTeacher) {
        
        NSLog(@"commentsToStu1 is failing");
    }
    
    NSString *commentsPath = [teacherPath stringByAppendingPathComponent:@"comments.txt"];
    
    NSString *commentStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"getCommentList\",\"uid\": \"%@\",\"kind\": \"%@\",\"page\": \"%@\"}", userID, kind, pageNum];
    
    BOOL commentResult = [commentStr writeToFile:commentsPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!commentResult) {
        
        NSLog(@"commentsToStu2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:commentsPath];
}

#pragma mark -- 评价订单
+(NSData *)createDataForOrderComments:(NSString *)orderID withTeacherID:(NSString *)teacherID withScore:(NSInteger)score withContents:(NSString *)contents withUserID:(NSString *)userID{
    
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"OrderComment1 is failing");
    }
    
    NSString *orderCommentPath = [userPath stringByAppendingPathComponent:@"orderComment.txt"];
    
    NSString *sendOrderCommentStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"commentTeacher\",\"orderId\": \"%@\",\"teacherId\": \"%@\",\"score\": \"%ld\",\"content\": \"%@\",\"uid\": \"%@\"}", orderID, teacherID, (long)score, contents, userID];
    
    BOOL orderCommentResult = [sendOrderCommentStr writeToFile:orderCommentPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!orderCommentResult) {
        
        NSLog(@"orderComment2 is failing");

    }
    return  [NSData dataWithContentsOfFile:orderCommentPath];
}

#pragma mark -- 更改手机
+(NSData *)createDataForChangePhoneNo:(NSString *)userID withCheckNo:(NSString *)checkNo withPhone:(NSString *)phone
{
    
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"changeNum1 is failing");
    }
    
    NSString *changeNumPath = [userPath stringByAppendingPathComponent:@"changeTelNum.txt"];
    
    NSString *changeNumStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"changePhone\",\"checkNo\": \"%@\",\"phone\": \"%@\",\"uid\": \"%@\"}", checkNo, phone, userID];
    
    BOOL changeResult = [changeNumStr writeToFile:changeNumPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!changeResult) {
        
        NSLog(@"changeNum2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:changeNumPath];
}

#pragma mark -- 修改邮箱
+(NSData *)createDataForChangeEmail:(NSString *)userID withCheckNo:(NSString *)checkNo withEmail:(NSString *)email
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"changeNum1 is failing");
    }
    
    NSString *changeNumPath = [userPath stringByAppendingPathComponent:@"changeTelNum.txt"];
    
    NSString *changeNumStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"changeEmail\",\"checkNo\": \"%@\",\"email\": \"%@\",\"uid\": \"%@\"}", checkNo, email, userID];
    
    BOOL changeResult = [changeNumStr writeToFile:changeNumPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!changeResult) {
        
        NSLog(@"changeNum2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:changeNumPath];
}

#pragma mark -- 更改密码
+(NSData *)createDataForChangePass:(NSString *)password withOldPassword:(NSString *)oldPassword withUid:(NSString *)uid{

    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);

    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"changeNum1 is failing");
    }
    
    NSString *changePasswordPath = [userPath stringByAppendingPathComponent:@"changePassword.txt"];
    
    NSString *newPass = [RSA encryptString:password publicKey:RSA_KEY];
    NSString *oldPass = [RSA encryptString:oldPassword publicKey:RSA_KEY];
    
    NSString *changePasswordStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"iosChangePassword\",\"password\": \"%@\",\"oldpassword\": \"%@\",\"uid\": \"%@\"}", newPass, oldPass, uid];
    
    BOOL changeResult = [changePasswordStr writeToFile:changePasswordPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!changeResult) {
        
        NSLog(@"changeNum2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:changePasswordPath];

}

#pragma mark -- 个人详细信息
+(NSData *)createDataForPersonalInfo:(NSString *)userID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"personalInfo1 is failing");
    }

    NSString *personalInfoPath =  [userPath stringByAppendingPathComponent:@"personalInfo.txt"];
    
    NSString *personalInfoStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"getInfo\",\"uid\": \"%@\"}", userID];
    
    BOOL personalResult = [personalInfoStr writeToFile:personalInfoPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!personalResult) {
        
        NSLog(@"personalInfo2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:personalInfoPath];
}

#pragma mark -- 修改个人信息
+(NSData *)createDataForChangeInfo:(NSString *)userID withNickname:(NSString *)nickName withName:(NSString *)name withAddress:(NSString *)address withEmail:(NSString *)email withResume:(NSString *)resume
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"changeInfo1 is failing");
    }

    NSString *changeInfoPath = [userPath stringByAppendingPathComponent:@"changeInfo.txt"];
    
    NSString *changeInfoStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"changeInfo\",\"uid\": \"%@\",\"nickName\": \"%@\",\"name\": \"%@\",\"address\": \"%@\",\"email\": \"%@\",\"resume\": \"%@\"}", userID, nickName, name, address, email, resume];
    
    BOOL changeInfoResult = [changeInfoStr writeToFile:changeInfoPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!changeInfoResult) {
        
        NSLog(@"changeInfo2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:changeInfoPath];
}

#pragma mark -- 导师修改导师主页相关信息
+(NSData *)createDataForChangeTeaInfo:(NSString *)userID withTeacherID:(NSString *)teacherID withperWeek:(NSString *)timeperweek withPrice:(NSString *)price withTalkway:(NSString *)talkWay withTime:(NSString *)time withAddress:(NSString *)address
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *teacherPath = [documentPath stringByAppendingPathComponent:@"teacher"];
    
    BOOL isTeacher = [[NSFileManager defaultManager] createDirectoryAtPath:teacherPath withIntermediateDirectories:YES attributes:nil error:nil];
    if (!isTeacher) {
        
        NSLog(@"changeTInfo1 is failing");
    }

    NSString *changeTeaInfoPath = [teacherPath stringByAppendingPathComponent:@"changeTeaInfo.txt"];
    
    NSString *changeTeaInfoStr = [NSString stringWithFormat:@"{\"style\": \"teacher\",\"method\": \"editTService\",\"uid\": \"%@\",\"teacherId\": \"%@\",\"timeperweek\": \"%@\",\"freetime\": \"%@\",\"talkWay\": \"%@\",\"price\": \"%@\",\"time\": \"%@\",\"address\": \"%@\"}", userID, teacherID, timeperweek, time, talkWay, price, time, address];
    
    if (![changeTeaInfoStr writeToFile:changeTeaInfoPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        NSLog(@"changeTInfo2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:changeTeaInfoPath];
}

#pragma mark -- 上传的图片保存到本地
+(NSString *)createDataForSavePhoto:(UIImage *)currentImage withName:(NSString *)imageName
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *savePath  = [documentPath stringByAppendingPathComponent:@"savePhoto"];
    NSLog(@"savePath = %@", savePath);
    
    BOOL isSave = [[NSFileManager defaultManager]createDirectoryAtPath:savePath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isSave) {
        
        NSLog(@"savePhoto1 is failing");
    }
    
    NSData *imageData = UIImageJPEGRepresentation(currentImage, 0.5);
    
    NSString *savePhotoPath = [savePath stringByAppendingPathComponent:imageName];
    
    BOOL saveResult = [imageData writeToFile:savePhotoPath atomically:YES];
    
    if (!saveResult) {
        
        NSLog(@"savePhoto2 is failing");
    }
    
    return savePhotoPath;
}

#pragma mark -- 保存图片
+(NSData *)createDataForSavePhoto:(NSString *)userID withiconUrl:(NSString *)iconUrl
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *savePath  = [documentPath stringByAppendingPathComponent:@"savePhoto"];
    //NSLog(@"savePath = %@", savePath);
    
    BOOL isSave = [[NSFileManager defaultManager]createDirectoryAtPath:savePath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isSave) {
        
        NSLog(@"updatePhoto1 is failing");
    }

    NSString *uploadPath = [savePath stringByAppendingPathComponent:@"update.txt"];
    
    NSString *uploadStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"changeIcon\",\"uid\": \"%@\", \"iconUrl\": \"%@\"}", userID, iconUrl];
    
    BOOL uploadResult = [uploadStr writeToFile:uploadPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!uploadResult) {
        
        NSLog(@"updatePhoto2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:uploadPath];
}

#pragma mark -- 心愿导师
//查看
+(NSData *)createDataForisLikeTutor:(NSString *)userID andTutorID:(NSString *)teacherID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"isLike1 is failing");
    }
    
    NSString *isLikePath = [userPath stringByAppendingPathComponent:@"isLikeTutor.txt"];
    
    NSString *isLikeStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"isLikeTeacher\",\"uid\": \"%@\",\"teacherId\": \"%@\"}", userID, teacherID];
    
    BOOL isLikeResult = [isLikeStr writeToFile:isLikePath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!isLikeResult) {
        
        NSLog(@"isLike2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:isLikePath];
}

//取消
+(NSData *)createDataForCancelTutor:(NSString *)userID andTutorID:(NSString *)teacherID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"cancelLike1 is failing");
    }
    
    NSString *cancelPath = [documentPath stringByAppendingPathComponent:@"cancelLike.txt"];
    
    NSString *cancelStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"dislikeTeacher\",\"uid\": \"%@\",\"teacherId\": \"%@\"}", userID, teacherID];
    
    BOOL cancelResult = [cancelStr writeToFile:cancelPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!cancelResult) {
        
        NSLog(@"cancelLike2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:cancelPath];
}

//收藏
+(NSData *)createDataForCollectTutor:(NSString *)userID andTutorID:(NSString *)teacherID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"collectLike1 is failing");
    }
    
    NSString *collectPath = [userPath stringByAppendingPathComponent:@"collectLike.txt"];
    
    NSString *collectStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"likeTeacher\",\"uid\": \"%@\",\"teacherId\": \"%@\"}", userID, teacherID];
    
    BOOL collectResult = [collectStr writeToFile:collectPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!collectResult) {
        
        NSLog(@"collectLike2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:collectPath];
}


//心愿单
+ (NSData *) createDataForCollectionLoving:(NSString *)userID page:(NSString *)pageID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath  = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"collectionLoving1 is failing");
    }
    
    NSString *collectPath = [userPath stringByAppendingPathComponent:@"collectionLoving.txt"];
    
    NSString *collectStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"getLikeTeachers\",\"uid\": \"%@\",\"page\": \"%@\"}", userID, pageID];
    
    BOOL collectResult = [collectStr writeToFile:collectPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!collectResult) {
        
        NSLog(@"collectionLoving2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:collectPath];
}

#pragma mark -- 介绍自己
+(NSData *)createDataForIntro:(NSString *)userID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *userPath = [documentPath stringByAppendingPathComponent:@"one_mile"];
    
    if (![[NSFileManager defaultManager] createDirectoryAtPath:userPath withIntermediateDirectories:YES attributes:nil error:nil]) {
        NSLog(@"intro1 is failing");
    }
    
    NSString *introPath = [userPath stringByAppendingPathComponent:@"intro.txt"];
    
    NSString *introStr = [NSString stringWithFormat:@"{\"style\": \"user\",\"method\": \"getIntroduce\",\"uid\": \"%@\"}", userID];
    
    if (![introStr writeToFile:introPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        NSLog(@"intro2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:introPath];
}

#pragma mark -- 订单

//创建订单
+(NSData *)createDataForCreateOrder:(NSString *)userID withQuestion:(NSString *)question withIntro:(NSString *)introduce withTeacherID:(NSString *)teacherID withSelectTime:(NSString *)selectTime withName:(NSString *)name withPhone:(NSString *)phone withEmail:(NSString *)email withContact:(NSString *)contact
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"createOrder1 is failing");
    }

    NSString *createOrderPath = [orderPath stringByAppendingPathComponent:@"createOrder.txt"];
    
    NSString *createOrderStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"createOrder\",\"uid\": \"%@\",\"question\": \"%@\",\"userIntroduce\": \"%@\",\"teacherId\": \"%@\",\"selectTime\": \"%@\",\"name\": \"%@\",\"phone\": \"%@\",\"email\": \"%@\",\"contact\": \"%@\"}", userID, question, introduce, teacherID, selectTime, name, phone, email, contact];
    
    BOOL createOrderResult = [createOrderStr writeToFile:createOrderPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!createOrderResult) {
        
        NSLog(@"createOrder2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:createOrderPath];
}

//导师拒绝接受服务
+(NSData *)createDataForTutorRefuse:(NSString *)userID withTeacherID:(NSString *)teacherID withOrderID:(NSString *)orderID withrefuseReason:(NSString *)reason
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"tutorRefuse1 is failing");
    }

    NSString *tutorRefusePath = [orderPath stringByAppendingPathComponent:@"tutorRefuse.txt"];
    
    NSString *tutorRefuseStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"refuseOrder\",\"uid\": \"%@\",\"teacherId\": \"%@\",\"orderId\": \"%@\",\"refuseReason\": \"%@\"}", userID, teacherID, orderID, reason];
    
    BOOL tutorRefuseResult = [tutorRefuseStr writeToFile:tutorRefusePath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!tutorRefuseResult) {
        
        NSLog(@"tutorRefuse2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:tutorRefusePath];
}

//导师双方已经约定好时间
+(NSData *)createDataForEnsureTime:(NSString *)userID withTeacherID:(NSString *)teacherID withOrderID:(NSString *)orderID withokTime:(NSString *)oktime
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"ensureTime1 is failing");
    }
    
    NSString *ensureTimePath = [orderPath stringByAppendingPathComponent:@"ensureTime.txt"];
    
    NSString *ensureTimeStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"ensureTime\",\"uid\": \"%@\",\"teacherId\": \"%@\",\"orderId\": \"%@\",\"okTime\": \"%@\"}", userID, teacherID, orderID, oktime];
    
    BOOL ensureResult = [ensureTimeStr writeToFile:ensureTimePath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!ensureResult) {
        
        NSLog(@"ensureTime2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:ensureTimePath];
}

/*
 *导师不同意退款disagreeOrder
 *导师同意退款agreeOrder
 *导师接受服务订单acceptOrder
 *导师确认双方没有约定好时间selectNoTime
 */
+(NSData *)createDataForTutorOrderAction:(NSString *)method withUserID:(NSString *)userID withTeacherID:(NSString *)teacherID withOrderID:(NSString *)orderID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"tutorOrderAction1 is failing");
    }

    NSString *tutorOrderPath = [orderPath stringByAppendingPathComponent:@"tutorOrderAction.txt"];
    
    NSString *tutorOrderStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"%@\",\"uid\": \"%@\",\"teacherId\": \"%@\",\"orderId\": \"%@\"}", method, userID, teacherID, orderID];
    
    BOOL tutorOrderResult = [tutorOrderStr writeToFile:tutorOrderPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!tutorOrderResult) {
        
        NSLog(@"tutorOrderAction2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:tutorOrderPath];
}

/*
 *用户满意服务satisfyOrder
 *用户不满意服务dissatisfyOrder
 *用户取消订单(未支付的时候)cancelOrder
 *用户取消订单(用户已经支付，导师尚未接受订单) cancelOrderAfterPay
 *用户取消订单(导师已经接受订单，但双方还没有商量好服务时间) cancelOrderAfterAccept
 *用户取消订单(双方已经确定好时间) cancelOrderAfterEnsure
 */
+(NSData *)createDataForClientOrderAction:(NSString *)method withUserID:(NSString *)userID withOrderID:(NSString *)orderID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"clientOrderAction1 is failing");
    }
    
    NSString *clientOrderPath = [orderPath stringByAppendingPathComponent:@"clientOrderAction.txt"];
    
    NSString *clientOrderStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"%@\",\"uid\": \"%@\",\"orderId\": \"%@\"}", method, userID, orderID];
    
    BOOL clientResult = [clientOrderStr writeToFile:clientOrderPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
    
    if (!clientResult) {
        
        NSLog(@"clientOrderAction2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:clientOrderPath];
}

//学员订单详情
+(NSData *)createDataForClientOrderDetail:(NSString *)userID withPage:(NSString *)page
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"clientOrderDetail1 is failing");
    }
    
    NSString *clientOrderDetailPath = [orderPath stringByAppendingPathComponent:@"clientOrderDetail.txt"];
    
    NSString *clientOrderDetailStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"getListByUser\",\"uid\": \"%@\",\"page\": \"%@\"}", userID, page];
    
    if (![clientOrderDetailStr writeToFile:clientOrderDetailPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        
        NSLog(@"clientOrderDetail2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:clientOrderDetailPath];
}
//学员订单详情(state)
+(NSData *)createDataForClientOrderDetail:(NSString *)userID withState:(NSString *)state withPage:(NSString *)page
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"clientOrderDetail1 is failing");
    }
    
    NSString *clientOrderDetailPath = [orderPath stringByAppendingPathComponent:@"clientOrderDetail.txt"];
    
    NSString *clientOrderDetailStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"getListByUser\",\"uid\": \"%@\",\"state\": \"%@\",\"page\": \"%@\"}", userID, state, page];
    
    if (![clientOrderDetailStr writeToFile:clientOrderDetailPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        
        NSLog(@"clientOrderDetail2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:clientOrderDetailPath];
}

//老师订单详情
+(NSData *)createDataForTutorOrderDetail:(NSString *)userID withTeacherID:(NSString *)teacherID withPage:(NSString *)page
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"tutorOrderDetail1 is failing");
    }
    
    NSString *tutorOrderDetailPath = [orderPath stringByAppendingPathComponent:@"tutorOrderDetail.txt"];
    
    NSString *tutorOrderDetailStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"getListByTeacher\",\"uid\": \"%@\",\"teacherId\": \"%@\",\"page\": \"%@\"}", userID, teacherID, page];
    
    if (![tutorOrderDetailStr writeToFile:tutorOrderDetailPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        
        NSLog(@"tutorOrderDetail2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:tutorOrderDetailPath];
}

//老师订单详情(state)
+(NSData *)createDataForTutorOrderDetail:(NSString *)userID withTeacherID:(NSString *)teacherID withState:(NSString *)state withPage:(NSString *)page
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"tutorOrderDetail1 is failing");
    }
    
    NSString *tutorOrderDetailPath = [orderPath stringByAppendingPathComponent:@"tutorOrderDetail.txt"];
    
    NSString *tutorOrderDetailStr = [NSString stringWithFormat:@"{\"style\": \"order\",\"method\": \"getListByTeacher\",\"uid\": \"%@\",\"teacherId\": \"%@\",\"state\": \"%@\",\"page\": \"%@\"}", userID, teacherID, state, page];
    
    if (![tutorOrderDetailStr writeToFile:tutorOrderDetailPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        
        NSLog(@"tutorOrderDetail2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:tutorOrderDetailPath];
}

#pragma mark -- 双方评价
+(NSData *)createDataForJudgement:(NSString *)style withMethod:(NSString *)method withOrderID:(NSString *)orderID withTeacherID:(NSString *)teacherID withScore:(NSInteger)score withContent:(NSString *)content withUserID:(NSString *)userID
{
    NSArray *array = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    NSString *documentPath = [array firstObject];
    
    NSString *orderPath  = [documentPath stringByAppendingPathComponent:@"order"];
    
    BOOL isUsers = [[NSFileManager defaultManager]createDirectoryAtPath:orderPath withIntermediateDirectories:YES attributes:nil error:nil];
    
    if (!isUsers) {
        
        NSLog(@"clientOrderAction1 is failing");
    }
    
    NSString *judgementPath = [orderPath stringByAppendingPathComponent:@"judgement.txt"];
    
    NSString *judgementStr = [NSString stringWithFormat:@"{\"style\": \"%@\",\"method\": \"%@\",\"orderId\": \"%@\",\"teacherId\": \"%@\",\"score\": \"%ld\",\"content\": \"%@\",\"uid\": \"%@\"}", style, method, orderID, teacherID, (long)score, content, userID];
    
    if (![judgementStr writeToFile:judgementPath atomically:YES encoding:NSUTF8StringEncoding error:nil]) {
        
        NSLog(@"clientOrderAction2 is failing");
    }
    
    return [NSData dataWithContentsOfFile:judgementPath];
}

#pragma mark -- 数据请求
+ (void) AFNConnectWithUrl:(NSString *)urlStr withBodyData:(NSData *)data connectBlock:(void (^)(id))myBlock
{
    NSString *urlEncoding = [urlStr stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    NSURL *url = [NSURL URLWithString:urlEncoding];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    //设置请求对象类型为Post请求
    [request setHTTPMethod:@"POST"];
    
    [request setHTTPBody:data];
    
    [NSURLConnection sendAsynchronousRequest:request queue:[NSOperationQueue mainQueue] completionHandler:^(NSURLResponse *response, NSData *data, NSError *connectionError) {
        
        myBlock(data);
        //NSLog(@"connectionError %@", connectionError);
    }];
}

@end
