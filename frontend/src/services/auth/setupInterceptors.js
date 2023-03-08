import axiosInstance from './library';
import TokenService from './token.service';

const setup = (store) => {
	axiosInstance.interceptors.request.use(
		(config) => {
			// console.log('we are in request');
			if (TokenService.checkLocalStorage()) {
				const token = TokenService.getLocalAccessToken();
				if (token) {
					config.headers['Authorization'] = 'Bearer ' + token;
				}
			}
			// console.log(config);
			return config;
		},
		(error) => {
			return Promise.reject(error);
		}
	);

	axiosInstance.interceptors.response.use(
		(response) => {
			return response;
		},
		async (err) => {
			const originalConfig = err.config;
			// console.log('we are in response');
			// console.log(err);

			if (originalConfig.url !== '/auth/signin' && err.response) {
				// if (err.response.status === 403) {
				// 	TokenService.removeUser();
				// 	// this.$router.push("/login");
				// }
				// // Access token was expired
				// else
				if (err.response.status === 401 && !originalConfig._retry) {
					originalConfig._retry = true;

					try {
						//the route from the api
						const rs = await axiosInstance.post(
							'/auth/refreshToken',
							TokenService.getLocalRefreshToken()
						);
						const { accessToken } = rs.data;

						//dipatching an action to make the mutation
						store.dispatch('auth/refreshToken', accessToken);
						// console.log('we are here');
						TokenService.updateLocalAccessToken(accessToken);

						return axiosInstance(originalConfig);
					} catch (_error) {
						store.dispatch('auth/logout');
						return Promise.reject(_error);
					}
				}
			}
			return Promise.reject(err);
		}
	);
};

export default setup;