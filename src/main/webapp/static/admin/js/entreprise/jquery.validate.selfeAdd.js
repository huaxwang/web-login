//jquery.validate 自定义验证方法

$(document)
		.ready(
				function() {

					/**
					 * 身份证号码验证
					 * 
					 */
					function isIdCardNo(num) {

						var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6,
								3, 7, 9, 10, 5, 8, 4, 2, 1);
						var parityBit = new Array("1", "0", "X", "9", "8", "7",
								"6", "5", "4", "3", "2");
						var varArray = new Array();
						var intValue;
						var lngProduct = 0;
						var intCheckDigit;
						var intStrLen = num.length;
						var idNumber = num;
						// initialize
						if ((intStrLen != 15) && (intStrLen != 18)) {
							return false;
						}
						// check and set value
						for (i = 0; i < intStrLen; i++) {
							varArray[i] = idNumber.charAt(i);
							if ((varArray[i] < '0' || varArray[i] > '9')
									&& (i != 17)) {
								return false;
							} else if (i < 17) {
								varArray[i] = varArray[i] * factorArr[i];
							}
						}

						if (intStrLen == 18) {
							// check date
							var date8 = idNumber.substring(6, 14);
							if (isDate8(date8) == false) {
								console.log('does not have date.');
								return false;
							}
							// calculate the sum of the products
							for (i = 0; i < 17; i++) {
								lngProduct = lngProduct + varArray[i];
							}
							// calculate the check digit
							intCheckDigit = parityBit[lngProduct % 11];
							// check last digit
							if (varArray[17] != intCheckDigit) {
								console.log('the check digit is not "' + intCheckDigit + '"');
								return false;
							}
						} else { // length is 15
							// check date
							var date6 = idNumber.substring(6, 12);
							if (isDate6(date6) == false) {

								return false;
							}
						}
						return true;

					}
					/**
					 * 判断是否为“YYYYMM”式的时期
					 * 
					 */
					function isDate6(sDate) {
						if (!/^[0-9]{6}$/.test(sDate)) {
							return false;
						}
						var year, month, day;
						year = sDate.substring(0, 4);
						month = sDate.substring(4, 6);
						if (year < 1700 || year > 2500)
							return false
						if (month < 1 || month > 12)
							return false
						return true
					}

					function dateFormat(dateValue) {
						reg = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
						if (!dateValue.match(reg)) {
							return false;
						} else {
							return true;
						}
					}

					// 日期验证
					jQuery.validator.addMethod("isDateF", function(value,
							element) {
						return this.optional(element) || dateFormat(value);
					}, "</br>请输入格式为yyyy-MM-dd的日期");

					/**
					 * 判断是否为“YYYYMMDD”式的时期
					 * 
					 */
					function isDate8(sDate) {
						if (!/^[0-9]{8}$/.test(sDate)) {
							return false;
						}
						var year, month, day;
						year = sDate.substring(0, 4);
						month = sDate.substring(4, 6);
						day = sDate.substring(6, 8);
						var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30,
								31, 30, 31 ]
						if (year < 1700 || year > 2500)
							return false
						if (((year % 4 == 0) && (year % 100 != 0))
								|| (year % 400 == 0))
							iaMonthDays[1] = 29;
						if (month < 1 || month > 12)
							return false
						if (day < 1 || day > iaMonthDays[month - 1])
							return false
						return true
					}
					// 身份证号码验证
					jQuery.validator.addMethod("idcardno", function(value,
							element) {
						return this.optional(element) || isIdCardNo(value);
					}, "</br>请输入正确的身份证号码");

					// 字母数字
					jQuery.validator.addMethod("alnum",
							function(value, element) {
								return this.optional(element)
										|| /^[a-zA-Z0-9]+$/.test(value);
							}, "只能包括英文字母和数字");

					// 邮政编码验证
					jQuery.validator.addMethod("zipcode", function(value,
							element) {
						var tel = /^[0-9]{6}$/;
						return this.optional(element) || (tel.test(value));
					}, "请正确填写邮政编码");

					// 汉字
					jQuery.validator.addMethod("chcharacter", function(value,
							element) {
						var tel = /^[\u4e00-\u9fa5]+$/;
						return this.optional(element) || (tel.test(value));
					}, "请输入汉字");

					// 字符最小长度验证（一个中文字符长度为2）
					jQuery.validator.addMethod("stringMinLength", function(
							value, element, param) {
						var length = value.length;
						for (var i = 0; i < value.length; i++) {
							if (value.charCodeAt(i) > 127) {
								length++;
							}
						}
						return this.optional(element) || (length >= param);
					}, $.validator.format("长度不能小于{0}!"));

					// 字符最大长度验证（一个中文字符长度为2）
					jQuery.validator.addMethod("stringMaxLength", function(
							value, element, param) {
						var length = value.length;
						for (var i = 0; i < value.length; i++) {
							if (value.charCodeAt(i) > 127) {
								length++;
							}
						}
						return this.optional(element) || (length <= param);
					}, $.validator.format("长度不能大于{0}!"));

					// 字符验证
					jQuery.validator.addMethod("string", function(value,
							element) {
						return this.optional(element)
								|| /^[\u0391-\uFFE5\w]+$/.test(value);
					}, "不允许包含特殊符号!");

					// 手机号码验证
					jQuery.validator
							.addMethod(
									"mobile",
									function(value, element) {
										var length = value.length;
										return this.optional(element)
												|| (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
														.test(value));
									}, "手机号码格式错误!");

					// 电话号码验证
					jQuery.validator.addMethod("phone",
							function(value, element) {
								var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
								return this.optional(element)
										|| (tel.test(value));
							}, "电话号码格式错误!");

					// 邮政编码验证
					jQuery.validator.addMethod("zipCode", function(value,
							element) {
						var tel = /^[0-9]{6}$/;
						return this.optional(element) || (tel.test(value));
					}, "邮政编码格式错误!");

					// 必须以特定字符串开头验证
					jQuery.validator.addMethod("begin", function(value,
							element, param) {
						var begin = new RegExp("^" + param);
						return this.optional(element) || (begin.test(value));
					}, $.validator.format("必须以 {0} 开头!"));

					// 不允许以特定字符串开头验证
					jQuery.validator.addMethod("notbegin", function(value,
							element, param) {
						var begin = new RegExp("^" + param);
						return this.optional(element) || !(begin.test(value));
					}, $.validator.format("不允许以 {0} 开头!"));

					// 验证两次输入值是否不相同
					jQuery.validator.addMethod("notEqualTo", function(value,
							element, param) {
						return value != $(param).val();
					}, $.validator.format("两次输入不能相同!"));

					// 验证值不允许与特定值等于
					jQuery.validator.addMethod("notEqual", function(value,
							element, param) {
						return value != param;
					}, $.validator.format("输入值不允许为{0}!"));

					// 验证值必须大于特定值(不能等于)
					jQuery.validator.addMethod("gt", function(value, element,
							param) {
						return value > param;
					}, $.validator.format("输入值必须大于{0}!"));

					// 验证值必须大于特定值(不能等于)
					jQuery.validator
							.addMethod(
									"gtl",
									function(value, element, param) {
										return (this.optional(element) || parseFloat(value) > parseFloat($(
												'#' + param).val()));
									}, $.validator
											.format("</br>输入值必须大于下限!且上下限不能相等"));

					// 验证值必须小于特定值(不能等于)
					jQuery.validator.addMethod("lt", function(value, element,
							param) {
						return value < param;
					}, $.validator.format("输入值必须小于{0}!"));

					// 验证值必须小于特定值(不能等于)
					jQuery.validator
							.addMethod(
									"ltl",
									function(value, element, param) {
										return (this.optional(element) || parseFloat(value) < parseFloat($(
												'#' + param).val()));
									}, $.validator
											.format("</br>输入值必须小于上限!且上下限不能相等"));

					// 验证值是否为非零正整数
					jQuery.validator.addMethod("positiveInteger", function(
							value, element) {
						var positiveInteger = /^[0-9]*[1-9][0-9]*$/;
						return this.optional(element)
								|| (positiveInteger.test(value));
					}, $.validator.format("<br/>输入值必须是非0正整数"));

					// 验证值是否为非零正数
					jQuery.validator.addMethod("positiveNumber", function(
							value, element) {
						var positiveNumber = /^([1-9]\d*|0)(\.\d*[1-9])?$/;
						return this.optional(element)
								|| (positiveNumber.test(value));
					}, $.validator.format("<br/>输入值必须是非0正数"));

					// 验证值小数位数不能超过两位
					jQuery.validator.addMethod("decimal", function(value,
							element) {
						var decimal = /^-?\d+(\.\d{1,6})?$/;
						return this.optional(element) || (decimal.test(value));
					}, $.validator.format("<br/>必须是数字且小数点不能超过六位"));

					// 验证值金额用,正数,小数点不能超过两位
					jQuery.validator.addMethod("unitprice", function(value,
							element) {
						var decimal = /^[+]?\d+(\.\d{1,6})?$/;
						return this.optional(element) || (decimal.test(value));
					}, $.validator.format("<br/>必须是正数且小数点不能超过六位"));
					// 验证值金额用,正数,小数点不能超过三位
					jQuery.validator.addMethod("purchaseprice", function(value,
							element) {
						var decimal = /^[+]?\d+(\.\d{1,6})?$/;
						return this.optional(element) || (decimal.test(value));
					}, $.validator.format("<br/>必须是正数且小数点不能超过六位"));
					
					//验证拆零数量，小数点前1到5位，小数点后不能超过三位
					jQuery.validator.addMethod("splitAmountCheck", function(value,
							element) {
						var decimal = /^\d{1,5}(\.\d{1,6})?$/;
						return this.optional(element) || (decimal.test(value));
					}, $.validator.format("<br/>必须是正数，整数位不能超过五位，且小数点不能超过六位"));
					
					//按月份验证药品有效期，不能超过120个月
					jQuery.validator.addMethod("matEXPMonth", function(value,
							element) {
						
						var decimal = /^[1-9]\d?$|^1[01]\d$|^120$/;
						
						return this.optional(element) || (decimal.test(value));
					}, $.validator.format("<br/>必须是正数，不能超过120个月"));
					
					//按天验证药品有效期，不能超过3650天
					jQuery.validator.addMethod("matEXPDay", function(value,
							element) {
						var decimal = /^[1-3]\d?$|^[1-9]\d$|^[1-9]\d\d$|^[1-2]\d\d\d$|^3[0-5]\d\d$|^3[0-6][0-4]\d$|^3650$/;
						return this.optional(element) || (decimal.test(value));
					}, $.validator.format("<br/>必须是正数，不能超过3650天"));
					
					//按年验证药品有效期，不能超过10年
					jQuery.validator.addMethod("matEXPYear", function(value,
							element) {
						var decimal = /^[1-9]$|^10$/;
						return this.optional(element) || (decimal.test(value));
					}, $.validator.format("<br/>必须是正数，不能超过10年"));
				});
