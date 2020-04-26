package cn.possible2dream.smartchat.serviceinter;

import cn.possible2dream.smartchat.entity.Staff;

public interface IStaffSV {
	Staff getStaff(int staffId);
	Staff getStaff(String code);
	int updateHead(Staff staff);
	void UpdateStaff(Staff staff);
	int updateState(Staff staff);
}
