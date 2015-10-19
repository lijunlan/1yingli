//
//  WantTobeTutorModel.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/10/15.
//  Copyright © 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface WantTobeTutorModel : NSObject

@property (nonatomic, copy) NSString *tobeName;
@property (nonatomic, copy) NSString *tobePhone;
@property (nonatomic, copy) NSString *tobeAddress;
@property (nonatomic, copy) NSString *tobeMail;

@property (nonatomic, strong) NSMutableDictionary *studyExperienceDic;
@property (nonatomic, strong) NSMutableDictionary *workExperienceDic;

@property (nonatomic, strong) NSMutableDictionary *serviceDic;

@end
