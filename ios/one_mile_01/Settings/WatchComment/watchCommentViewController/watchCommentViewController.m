//
//  watchCommentViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "watchCommentViewController.h"
#import "DOPDropDownMenu.h"

@interface watchCommentViewController ()<DOPDropDownMenuDataSource, DOPDropDownMenuDelegate>

@property (nonatomic, strong) NSArray *waitingState;

@end

@implementation watchCommentViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.waitingState = @[@"我给出的评价", @"我收到的评价"];
        self.giveCommentArray = [NSMutableArray array];
        self.receiveCommentArray = [NSMutableArray array];
        self.nextPage = 1;

    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor colorWithRed:237/255.0 green:239/255.0 blue:240/255.0 alpha:1.0];
    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 38, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backbutton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
    titleLabel.text = @"查看评价";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    //    学员评价tableView
    self.watchCommentTV = [[UITableView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y + 120, self.view.frame.size.width, self.view.frame.size.height - 130) style:UITableViewStylePlain];
    
    self.watchCommentTV.showsVerticalScrollIndicator = NO;
    self.watchCommentTV.backgroundColor = [UIColor clearColor];
    self.watchCommentTV.delegate = self;
    self.watchCommentTV.dataSource = self;
    self.watchCommentTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.watchCommentTV];
    
    [self createSubviews];
    //[self getwatchCommentData];
    
    [self.watchCommentTV addHeaderWithTarget:self action:@selector(headerRereshing)];
    [self.watchCommentTV headerBeginRefreshing];
    
    [self.watchCommentTV addFooterWithTarget:self action:@selector(TutorfooterRefreshing)];
    // Do any additional setup after loading the view.
}

#pragma mark -- 请求数据
-(void)getwatchCommentData{
    
    if (self.watchCommentS == giveComment) {
        self.kind = @"1";
    }else if (self.watchCommentS == recevieComment){
        self.kind = @"2";
    }
    self.detailId = [[[NSUserDefaults standardUserDefaults]objectForKey:@"userInfo"] objectForKey:@"uid"];
    
    NSString *nextP = [NSString stringWithFormat:@"%ld",(long)self.nextPage];

     [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForStuCommentList:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withPage:nextP withKind:self.kind] connectBlock:^(id data) {
         
         NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];

         if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
             
             NSArray *dataArray = [[dic objectForKey:@"data"] objectFromJSONString];
             
             for (NSMutableDictionary *dic in dataArray) {
                 
                 watchCommentModel *watchCommentM = [[watchCommentModel alloc] init];
                 
                 watchCommentM.commentId = [dic objectForKey:@"commentId"];
                 watchCommentM.content = [dic objectForKey:@"content"];
                 watchCommentM.score = [dic objectForKey:@"score"];
                 watchCommentM.createTime = [dic objectForKey:@"createTime"];
                 watchCommentM.name = [dic objectForKey:@"name"];
                 watchCommentM.teacherId = [dic objectForKey:@"teacherId"];
                 watchCommentM.url = [dic objectForKey:@"url"];
                 
                 if (self.watchCommentS == giveComment ) {
                     
                     [self.giveCommentArray addObject:watchCommentM];
                     
                 }else if(self.watchCommentS == recevieComment){
                     
                     [self.receiveCommentArray addObject:watchCommentM];
                     
                 }
             }
         }
         [self.watchCommentTV reloadData];
     }];
}

#pragma mark -- tableviewDelegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    if (self.watchCommentS == giveComment) {
        
        return self.giveCommentArray.count;
        
    }else {
        return self.receiveCommentArray.count;
   }
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 160;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"myCell";
    
    watchCommentTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell =[[watchCommentTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
        cell.backgroundColor = [UIColor clearColor];
    }
    if (self.watchCommentS == recevieComment) {
        
        if (_receiveCommentArray.count == 0) {
            return cell;
        }
        watchCommentModel *tmp = [self.receiveCommentArray objectAtIndex:indexPath.row];
        cell.watchCommentM = tmp;
        
    }else if (self.watchCommentS == giveComment){
        
        if (_giveCommentArray.count == 0) {
            return cell;
        }
        watchCommentModel *tmp = [self.giveCommentArray objectAtIndex:indexPath.row];
        cell.watchCommentM = tmp;
     

    }
    return cell;
}

-(void)createSubviews
{
    //添加下拉菜单

    DOPDropDownMenu *waitMenu = [[DOPDropDownMenu alloc] initWithOrigin:CGPointMake(8, NAVIGATIONHEIGHT + 5) andHeight:40];
    waitMenu.layer.masksToBounds = YES;
    waitMenu.layer.cornerRadius = 20.0f;
    [self.view addSubview:waitMenu];
    
    waitMenu.dataSource = self;
    waitMenu.delegate = self;
    
}

#pragma mark -- 第三方方法
-(NSInteger)numberOfColumnsInMenu:(DOPDropDownMenu *)menu
{
    return 1;
}

-(NSInteger)menu:(DOPDropDownMenu *)menu numberOfRowsInColumn:(NSInteger)column
{
    return _waitingState.count;
}

-(NSString *)menu:(DOPDropDownMenu *)menu titleForRowAtIndexPath:(DOPIndexPath *)indexPath
{
    return self.waitingState[indexPath.row];
}

-(void)menu:(DOPDropDownMenu *)menu didSelectRowAtIndexPath:(DOPIndexPath *)indexPath
{
    
    switch (indexPath.row) {
            
        case 0:
            self.watchCommentS = giveComment;
            [self.watchCommentTV headerBeginRefreshing];
            break;
            
        case 1:
            self.watchCommentS = recevieComment;
            [self.watchCommentTV headerBeginRefreshing];
            break;

    }
    [self.watchCommentTV reloadData];
}


-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.tabBarController.tabBar.hidden = YES;
}


-(void)backbutton:(UIButton *)button{
    
        [self.navigationController popViewControllerAnimated:YES];
}


#pragma mark -- 下拉刷新 上拉加载更多
//下拉刷新
-(void)headerRereshing{
    
    self.isUpLoading = NO;//标记为下拉操作
    self.nextPage = 1;
    if (self.watchCommentS == giveComment) {
        
        [self.giveCommentArray removeAllObjects];
        
    }else {
        [self.receiveCommentArray removeAllObjects];
    }
    
    [self getwatchCommentData];//重新请求数据
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.watchCommentTV reloadData];
        
        [self.watchCommentTV headerEndRefreshing];
//    });
}

//上拉加载更多
- (void)TutorfooterRefreshing
{
    self.nextPage++;
    self.isUpLoading = YES;//标记为上拉操作
    
    [self getwatchCommentData];//请求数据
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.watchCommentTV reloadData];
        
        [self.watchCommentTV footerEndRefreshing];
//    });
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
