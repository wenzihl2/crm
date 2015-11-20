function checkPassword(b) {
	var a = new Object();
	if (isEmptyPassword(b)) {
		a.success = false;
		a.msg = "请输入6-20位密码！";
		return a;
	}
	if (isTooShort(b)) {
		a.success = false;
		a.msg = "请输入6-20位密码！";
		return a;
	}
	if (isAllSameChars(b)) {
		a.success = false;
		a.msg = "不能为同一字符！";
		return a;
	}
	if (hasAllIncreaseChars(b) || hasAllDecreaseChars(b)) {
		a.success = false;
		a.msg = "不能输入连续字符！";
		return a;
	}
	if (isAllNum(b)) {
		a.success = false;
		a.msg = "不能为纯数字！";
		return a;
	}
	if (isAllLetter(b)) {
		a.success = false;
		a.msg = "不能为纯字母！";
		return a;
	}
	if (isAllSymbol(b)) {
		a.success = false;
		a.msg = "不能为纯符号！";
		return a;
	}
	if (hasIllegalSymbol(b)) {
		a.success = false;
		a.msg = "密码只能由英文、数字及符号组成！";
		return a;
	}
	a.success = true;
	a.msg = "OK.";
	return a;
}
function isEmptyPassword(a) {
	return a == null || a.length == 0;
}
function isTooShort(a) {
	return a.length < 6;
}
function hasNum(b) {
	var a = /\d/;
	return a.test(b);
}
function isAllNum(b) {
	var a = /^\d+$/;
	return a.test(b);
}
function hasLetter(b) {
	var a = /[a-zA-Z]/;
	return a.test(b);
}
function isAllLetter(b) {
	var a = /^[a-zA-Z]+$/;
	return a.test(b);
}
function hasSymbol(b) {
	var a = /[^0-9a-zA-Z\s<>;'\\]/;
	return a.test(b);
}
function isAllSymbol(b) {
	var a = /^[^0-9a-zA-Z\s<>;'\\]+$/;
	return a.test(b);
}
function hasSpace(b) {
	var a = /\s/g;
	return a.test(b);
}
function hasIllegalSymbol(c) {
	var b = /[\s<>;'\\]/;
	var a = /[\u4e00-\u9fa5]/;
	return b.test(c) || a.test(c);
}
function isAllSameChars(d) {
	if (d == null || d.length < 2) {
		return false;
	}
	var c = d.charCodeAt(0);
	var b = 1;
	for (b = 1; b < d.length; b++) {
		var a = d.charCodeAt(b);
		if (a != c) {
			return false;
		}
	}
	return true;
}
function hasRepeat6Chars(b) {
	var a = /(.)\1\1\1\1\1/;
	return a.test(b);
}
function hasIncrease6Chars(e) {
	if (e == null || e.length < 6) {
		return false;
	}
	var d = e.charCodeAt(0);
	var c = 1;
	var b = 1;
	for (b = 1; b < e.length; b++) {
		var a = e.charCodeAt(b);
		if (a == d + 1) {
			c++;
			if (c >= 6) {
				return true;
			}
		} else {
			c = 1;
		}
		d = a;
	}
	return false;
}
function hasDecrease6Chars(e) {
	if (e == null || e.length < 6) {
		return false;
	}
	var d = e.charCodeAt(0);
	var c = 1;
	var b = 1;
	for (b = 1; b < e.length; b++) {
		var a = e.charCodeAt(b);
		if (a == d - 1) {
			c++;
			if (c >= 6) {
				return true;
			}
		} else {
			c = 1;
		}
		d = a;
	}
	return false;
}
function hasAllIncreaseChars(f) {
	if (f == null) {
		return false;
	}
	var d = f.length;
	var e = f.charCodeAt(0);
	var c = 1;
	var b = 1;
	for (b = 1; b < f.length; b++) {
		var a = f.charCodeAt(b);
		if (a == e + 1) {
			c++;
			if (c >= d) {
				return true;
			}
		} else {
			c = 1;
		}
		e = a;
	}
	return false;
}
function hasAllDecreaseChars(f) {
	if (f == null) {
		return false;
	}
	var d = f.length;
	var e = f.charCodeAt(0);
	var c = 1;
	var b = 1;
	for (b = 1; b < d; b++) {
		var a = f.charCodeAt(b);
		if (a == e - 1) {
			c++;
			if (c >= d) {
				return true;
			}
		} else {
			c = 1;
		}
		e = a;
	}
	return false;
}
function checkPasswordStrength(b) {
	var a = "A";
	if (isTooShort(b)) {
		a = "A";
	} else {
		if (hasNum(b) && hasLetter(b) && hasSymbol(b)) {
			a = "C";
		} else {
			if (hasNum(b) && hasLetter(b)) {
				a = "B";
			} else {
				if (hasNum(b) && hasSymbol(b)) {
					a = "B";
				} else {
					if (hasSymbol(b) && hasLetter(b)) {
						a = "B";
					} else {
						if (isAllNum(b) || isAllLetter(b) || isAllSymbol(b)) {
							a = "A";
						}
					}
				}
			}
		}
	}
	return a;
};