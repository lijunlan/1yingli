//
//  CampusViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "CampusViewController.h"
#import "CampusTableViewCell.h"
#import "CampusModel.h"
#import "tutorHomeViewController.h"

@interface CampusViewController ()

@property (nonatomic, strong) NSMutableArray *campusArray;
@property (nonatomic, copy) NSString *campusFirstID;
@property (nonatomic, copy) NSString *campusLastID;
@property (nonatomic, assign) BOOL downUpdata;

@end

@implementation CampusViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.campusArray = [NSMutableArray array];
        self.campusFirstID = @"min";
        self.campusLastID = @"max";
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    
    [self setSubviews];
    
    self.downUpdata = YES;
    [self getListData:self.campusFirstID withisFirst:YES];
}

-(void)setSubviews
{
    UIButton *pushBT = [UIButton buttonWithType:UIButtonTypeCustom];
    pushBT.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    //[pushBT setBackgroundColor:[UIColor blackColor]];
    [pushBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [pushBT addTarget:self action:@selector(popToRoot:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:pushBT];
    
    UILabel *pilotTitleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 40, pushBT.frame.origin.y, 80, pushBT.frame.size.height)];
    pilotTitleL.text = @"校园生活";
    pilotTitleL.font = [UIFont systemFontOfSize:18.0f];
    [self.view addSubview:pilotTitleL];
    
    self.campusTV = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT) style:UITableViewStylePlain];
    self.campusTV.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.campusTV.showsVerticalScrollIndicator = NO;
    self.campusTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [self.view addSubview:self.campusTV];
    
    self.campusTV.dataSource = self;
    self.campusTV.delegate = self;
}

-(void)popToRoot:(UIButton *)button
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewDidDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
    
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_campusArray.count == 0) {
        
        return 0;
    }
    
    return _campusArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *campusCellIdentifier = @"campusCell";
    
    CampusTableViewCell *campusCommonCell = [tableView dequeueReusableCellWithIdentifier:campusCellIdentifier];
    
    if (campusCommonCell == nil) {
        campusCommonCell = [[CampusTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:campusCellIdentifier];
        campusCommonCell.selectionStyle = UITableViewCellSelectionStyleNone;
        campusCommonCell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    }
    
    if (_campusArray.count == 0) {
        
        return campusCommonCell;
    }
    
    CampusModel *tmp = [_campusArray objectAtIndex:indexPath.row];
    [campusCommonCell.campusTutorIV sd_setImageWithURL:[NSURL URLWithString:[tmp.iconUrl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    
    campusCommonCell.campusTutorNameL.text = tmp.name;
    campusCommonCell.topicForCampusL.text = tmp.serviceTitle;
    
//    campusCommonCell.schoolForCampusL.text = tmp.schoolName;
//    
//    campusCommonCell.positionForCampusL.text = tmp.major;
    campusCommonCell.simInfoForCampusL.text = [NSString stringWithFormat:@"%@ %@", tmp.schoolName, tmp.major];
    
    campusCommonCell.hasSeenForCampusL.text = [NSString stringWithFormat:@"%@人见过", tmp.likeNo];
    campusCommonCell.clickNumberForCampusL.text = [NSString stringWithFormat:@"本周可咨询%@次", tmp.timeperweek];
    
    return campusCommonCell;
}

#pragma mark --进入导师详情页面
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_campusArray.count == 0) {
        return;
    }
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    CampusModel *tmp = [_campusArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = tmp.teacherId;
    tutorVC.serviceTitleStr = tmp.serviceTitle;
    tutorVC.campusM = tmp;
    [self.navigationController pushViewController:tutorVC animated:YES];
}

#pragma mark --请求数据
-(void)getListData:(NSString *)userId withisFirst:(BOOL)isFirst
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForList:@"8" withNextID:userId withdownUpdata:_downUpdata withisFirst:isFirst] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        //NSLog(@"==========result = %@",result);
        
        NSString *dataAgain = [result objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[dataAgain objectFromJSONString];
        
        for (NSDictionary *dic in jsonArray) {
            
            CampusModel *campusM = [[CampusModel alloc] init];
            
            campusM.iconUrl = [dic objectForKey:@"iconUrl"];
            campusM.level = [dic objectForKey:@"level"];
            campusM.likeNo = [dic objectForKey:@"likeNo"];
            campusM.name = [dic objectForKey:@"name"];
            campusM.serviceTitle = [dic objectForKey:@"serviceTitle"];
            campusM.serviceContent = [dic objectForKey:@"serviceContent"];
            campusM.simpleShow1 = [dic objectForKey:@"simpleShow1"];
            campusM.simpleShow2 = [dic objectForKey:@"simpleShow2"];
            campusM.teacherId = [dic objectForKey:@"teacherId"];
            campusM.companyName = [dic objectForKey:@"companyName"];
            campusM.position = [dic objectForKey:@"position"];
            campusM.schoolName = [dic objectForKey:@"schoolName"];
            campusM.major = [dic objectForKey:@"major"];
            campusM.timeperweek = [dic objectForKey:@"timeperweek"];
            
            [self.campusArray addObject:campusM];
        }
        
        [self.campusTV reloadData];
    }];
}

-(void)showAlert:(NSString *)message
{
    UIAlertView *promptAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:message delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
    
    [NSTimer scheduledTimerWithTimeInterval:0.8f target:self selector:@selector(timerFireMethod:) userInfo:promptAlert repeats:YES];
    
    [promptAlert show];
}

-(void)timerFireMethod:(NSTimer *)theTimer
{
    UIAlertView *promptAlert = (UIAlertView *)[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
