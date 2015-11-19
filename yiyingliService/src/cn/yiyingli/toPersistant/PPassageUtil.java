package cn.yiyingli.toPersistant;

import java.util.Calendar;

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;

public class PPassageUtil {

	public static void toSavePassage(Teacher teacher, String title, String tag, String content, String imageUrl,
			Passage passage) {
		passage.setContent(content);
		passage.setEditorName(teacher.getName());
		passage.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		passage.setImageUrl(imageUrl);
		passage.setLikeNumber(0L);
		passage.setLookNumber(0L);
		passage.setOwnTeacher(teacher);
		passage.setOnshow(false);
		passage.setState(PassageDao.PASSAGE_STATE_CHECKING);
		passage.setTag(tag);
		passage.setTitle(title);
	}
}
