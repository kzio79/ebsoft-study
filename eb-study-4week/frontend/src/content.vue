<script setup>
// created 라이프사이클 훅에서 API 요청
// eslint-disable-next-line no-unused-vars
import { computed, inject, onMounted, ref } from "vue";
import axios from "axios";
import router from '@/router'
import { useRoute } from 'vue-router'
import apiService from "@/service/apiService";

// 전역변수 Url
const $serverUrl = inject('$serverUrl')

// data 속성 정의
let viewBoardContent = ref(null)
// eslint-disable-next-line no-unused-vars
let viewReplyContent = []
let writeReplyContent = []
let viewListFile = []

const categoryNames = ['카테고리', 'JAVA', 'JS', 'SpringBoot', 'Android']

const route = useRoute()
const boardNum = route.query.boardNum

//computed로 생성된 값은 그 값에 의존하는 다른 값이 변경될 때마다 자동으로 업데이트
let viewContent = computed(() => viewBoardContent.value)

const viewContentAndReply = async () => {

  try {
    const response = await apiService.getContent(boardNum)
    viewBoardContent.value = response.data.viewBoardContent
    viewReplyContent = response.data.viewReplyContent
    viewListFile = response.data.viewListFile

  } catch (error) {
    console.log("댓글 전송 에러 : ", error.response || error.message)
  }
}

//reply 등록
const writeReply = async () => {
  try {
    await apiService.postReply(boardNum,writeReplyContent)

    writeReplyContent=''
    await viewContentAndReply()
  } catch (error){
    console.log("댓글 전송 에러 : "+ error)
  }
}

onMounted(async () => {
  await viewContentAndReply()
})

const pageIndex = () => {
  router.push({ name: 'index' })
}

const modifyContent = (boardNum) => {
  router.push({ name: 'modify', query: { boardNum: boardNum } })
}

const deleteContent = (boardNum) => {
  router.push({ name: 'delete', query: { boardNum: boardNum } })
}

const formatDate = (dataString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' }
  return new Date(dataString).toLocaleDateString(undefined, options)
}
</script>

<template>
  <div v-if="viewContent">
    <h1>게시판 - 보기</h1>
    <div align="center" style="margin-top: 2%; width: 80%">
      <div style="display: flex; justify-content: space-between; margin-left: 10%">
        <td id="writer" name="writer" style="color: black; font-weight: bolder" aria-readonly="true" >
          작성자 : [{{ viewContent.writer }}]
        </td>
        <div>
          <td name="writeDate" style="align-items: end; color: black; font-weight: bolder" aria-readonly="true"
          >
                        등록일시 {{ formatDate(viewContent.writeDate )}}&nbsp;&nbsp;
          </td>

          <td name="modifyDate" style="color: black; font-weight: bolder" aria-readonly="true">
                        수정일시 {{ formatDate(viewContent.modifyDate) }}
          </td>
        </div>
      </div>
      <div style="display: flex; justify-content: space-between; margin-left: 10%">
        <td name="categoryNum" style="color: black; font-weight: bolder" aria-readonly="true">
                    [{{categoryNames[viewContent.categoryNum]}}]&nbsp;{{viewContent.title}}
        </td>
        <div>
          <td name="hit" style="color: black; font-weight: bolder" aria-readonly="true">
                        조회수 {{ viewContent.hit }}
          </td>
        </div>
      </div>
    </div>
    <hr />
    <div>
      <div align="center" style="margin-top: 1%">
        <form
          action="/modify"
          name="regform"
          style="margin-bottom: 5%; margin-top: 2%"
        >
          <table style="width: 80%">
            <tr>
              <td style="color: black; font-weight: bolder">
                <textarea rows="12" style="width: 100%" class="click" name="content" v-model="viewContent.content" readonly> </textarea>
              </td>
            </tr>

            <!--            fileView, fileDownload-->
            <tr>
              <ul>
                <td style="color: black; font-weight: bolder">
                  <br />
                  등록된 파일
                  <br />
                    <tr v-for="(file, index) in viewListFile" :key="index">
                      <td>
                        <a :href="`${$serverUrl}downloadFile?boardNum=${boardNum}&fileNum=${file.fileNum}`" style="text-decoration: none; color: black">
<!--                        <a :href="apiService.getDownloadFile(boardNum, file.fileNum)" style="text-decoration: none; color: black">-->
                        {{ file.originalFileName}}
                        </a>
                      </td>
                    </tr>
                </td>
              </ul>
            </tr>

            <div align="center">
              <button @click="pageIndex">목록</button>
              <button @click="modifyContent(boardNum)">수정</button>
              <button @click="deleteContent(boardNum)">삭제</button>
            </div>
          </table>
        </form>

        <!--        댓글부분-->
        <div>
          <div align="center" style="margin: 2%">
            <form id="reply" @submit.prevent="writeReply">
              <table style="border: 1px solid #dddddd; width: 80%">
                <tr v-for="(viewReplyContent, boardNum) in viewReplyContent" :key="boardNum">
                  <td>{{ viewReplyContent.replyDate }}<br>
                    {{ viewReplyContent.content }}
                    <hr /></td>
                </tr>
                <tr>
                  <td style="align-items: center" valign="middle"></td>
                  <td style="color: black; font-weight: bolder">
                    <textarea rows="2" style="width: 100%" v-model="writeReplyContent" placeholder="댓글을 입력해 주세요"></textarea>
                  </td>
                  <td>
                    <input type="hidden" name="boardNum" :value="boardNum"/>
                    <input type="submit" value="등록" />
                  </td>
                </tr>
              </table>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>