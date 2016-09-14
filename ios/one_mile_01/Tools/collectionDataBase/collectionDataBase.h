//
//  collectionDataBase.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/7.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "StudyPilotModel.h"
@interface collectionDataBase : NSObject

SingletonH(collectionDataBase);

-(void)insertStudyPilotModel:(StudyPilotModel *)StudyPilotModel;
- (NSMutableArray *)selectAllStudyPilotModel;
- (void)deleteAllStudyPilot;
- (StudyPilotModel *)selectWithteacherId:(NSString *)teacherId;
- (void)deleteFromDBWithTeacherId:(NSString *)teacherId;
@end
