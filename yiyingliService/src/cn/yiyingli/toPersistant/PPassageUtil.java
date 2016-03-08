package cn.yiyingli.toPersistant;

import java.util.Calendar;

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;

public class PPassageUtil {

	public static void toSavePassageByTeacher(Teacher teacher, String title, String tag, String summary, String content,
			String imageUrl, Passage passage) {
		toSavePassage(teacher, title, tag, summary, content, imageUrl, passage);
		passage.setOnshow(true);
		passage.setRemove(false);
		passage.setState(PassageDao.PASSAGE_STATE_CHECKING);
	}

	private static void toSavePassage(Teacher teacher, String title, String tag, String summary, String content,
			String imageUrl, Passage passage) {
		passage.setOnReward(false);
		passage.setRewardNumber(0L);
		passage.setContent(content);
		passage.setSummary(summary);
		passage.setEditorName(teacher.getName());
		passage.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		passage.setImageUrl(imageUrl);
		passage.setLikeNumber(0L);
		passage.setLookNumber(0L);
		passage.setOwnTeacher(teacher);
		passage.setTag(tag);
		passage.setTitle(title);
	}

	public static void toEditPassageByManager(String title, String tag, String summary, String content, String imageUrl,
			boolean onshow, boolean onReward, Passage passage) {
		passage.setContent(content);
		passage.setSummary(summary);
		passage.setImageUrl(imageUrl);
		passage.setTag(tag);
		passage.setTitle(title);
		passage.setOnshow(onshow);
		passage.setOnReward(onReward);
	}

	public static void toSavePassageByManager(Teacher teacher, String title, String tag, String summary, String content,
			String imageUrl, Passage passage) {
		toSavePassage(teacher, title, tag, summary, content, imageUrl, passage);
		passage.setOnshow(true);
		passage.setRemove(false);
		passage.setState(PassageDao.PASSAGE_STATE_OK);
	}
}
