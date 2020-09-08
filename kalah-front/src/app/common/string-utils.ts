
export function filterObjToQueryParams(data: Partial<any>) {
  return Object.keys(data).map(key => {
    if (data[key] instanceof Array) {
      return (data[key] as Array<any>).map(element => `${key}=${element}`).join('&');
    } else {
      return data[key]
        ? `${key}=${data[key] != null ? data[key] : ''}`
        : '';
    }
  }).join('&');
}
