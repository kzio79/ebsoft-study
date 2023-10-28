import axios from "axios";


const BASE_URL = "http://localhost:8082/" ;

export default {

  /**
   * 리스트 보여주기
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  async getBoardList(params){
    const response = await axios.get(`${BASE_URL}api/v1/index`, {params});
    return response.data;
  },

  /**
   * 선탣한 컨텐츠 보여주기
   * @param boardNum
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  async getContent(boardNum){
    const response =  await axios.get(`${BASE_URL}api/v1/content`, {params : { boardNum: boardNum } })
    return response.data;
  },

  /**
   * 파일 다운로드
   * @param boardNum
   * @param fileNum
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  async getDownloadFile(boardNum, fileNum){
    return await axios.get(`${BASE_URL}downloadFile`, {params: {boardNum: boardNum, fileNum: fileNum}});
  },

  /**
   * 글 수정
   * @param boardNum
   * @param viewContent
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  async postModify(boardNum, viewContent){
    const response =await axios.post(`${BASE_URL}api/v1/modify`, JSON.stringify({
      boardNum: boardNum,
      categoryNum: viewContent.value.categoryNum,
      writer: viewContent.value.writer,
      pw: viewContent.value.pw,
      title: viewContent.value.title,
      content: viewContent.value.content,
    }), {
      headers: {"Content-Type": "application/json"}
    })

    return response.data;
  },

  /**
   * 글 등록
   * @param categoryNum
   * @param writer
   * @param pw
   * @param title
   * @param content
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  async postWrite(categoryNum, writer, pw, title, content){
    const response = await axios.post(`${BASE_URL}api/v1/write`, JSON.stringify({
        categoryNum: categoryNum.value,
        writer: writer.value,
        pw: pw.value,
        title: title.value,
        content: content.value
      }),
      {
        headers: { "Content-Type": "application/json" }
      })
    return response.data;
  },

  /**
   * 글 삭제
   * @param boardNum
   * @param pw
   * @returns {Promise<void>}
   */
  async postDelete(boardNum, pw){
    await axios.post(`${BASE_URL}api/v1/delete`, {
      boardNum: boardNum,
      pw: pw.value
    })
  },

  /**
   * 파일 업로드
   * @param formData
   * @returns {Promise<void>}
   */
  async postFile(formData) {
    await axios.post(`${BASE_URL}insertFile`, formData, {
      headers: { "Content-Type": "multipart/form-data" }
    })
  },

  /**
   * 댓글 업로드
   * @param boardNum
   * @param content
   * @returns {Promise<void>}
   */
  async postReply(boardNum, content){
    await axios.post(`${BASE_URL}api/v1/content`, {
      boardNum: boardNum,
      content: content
    })
  }

}