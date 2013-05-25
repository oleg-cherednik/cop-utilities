package cop.ifree.test.vehicleservice.json;

/** Codes that might be returned as a result of WS method invocation */
public enum OperationOutputCodes {
	/** Успешное завершение */
	OK,
	/** Общая ошибка сервреа */
	SYSTEM_ERROR,
	/** Неверные параметры в запросе */
	INVALID_PARAMETERS,
	/** Объект не найден в БД */
	NOT_FOUND,
	/** Недостаточно прав для выполнения действия */
	PERMISSION_DENIED,
	/** Объект с таким именем уже существует */
	NON_UNIQUE_NAME,
	/** Объект с таким кодом уже существует */
	NON_UNIQUE_CODE,
	/** Пользователь не может выполнить действие над собой */
	NOT_PERMITTED_ACTION_ON_YOURSELF,
	/** Время действия пароля истекло */
	USER_PASSWORD_EXPIRED,
	/** Логин или пароль не верен */
	AUTHORIZATION_INVALID_LOGIN_OR_PASSWORD,
	/** Пользователь заблокирован флагом */
	AUTHORIZATION_USER_BLOCKED,
	/** Пользователь временно заблокирован */
	AUTHORIZATION_USER_BANNED,
	/** Пользователь долго не авторизовывался */
	AUTHORIZATION_ACCOUNT_EXPIRED,
	/** У пользователя не задан пароль */
	AUTHORIZATION_USER_NEVER_LOGINED,
	/** Версия списка в кэше соответствует запрошенной версии */
	NO_DIFFERENCES_IN_CACHE,
	/** Невозможно скомпилировать выражение */
	CANT_COMPILE_EXPRESSION
}
