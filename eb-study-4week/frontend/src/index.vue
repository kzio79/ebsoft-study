<script setup>
import { useRoute, useRouter } from 'vue-router'
import { onMounted, ref, watch } from "vue";
import apiService from "@/service/apiService";

const route = useRoute()

// data 속성 정의
let getContentList = []
let totalCount = ref(null)
let paging = ref({
  block: 0,
  endPage: 0,
  nextBlock: 0,
  pageNum: null,
  pageSize: null,

})

/**
 * 검색을 위한 조건
 * @type {Ref<UnwrapRef<string>>}
 */
const searchId = ref('');
const categoryNum = ref(0);
const startDate = ref(null);
const endDate = ref(new Date().toISOString().substring(0, 10));

// created 라이프사이클 훅에서 API 요청
const search = async () => {

  paging.value.pageNum = route.query.pageNum ? Number(route.query.pageNum) : 1;
  paging.value.pageSize =  route.query.size ? Number(route.query.size) : 10;

  try {
    const response = await apiService.getBoardList({
      searchId: searchId.value,
      categoryNum: categoryNum.value,
      startDate: startDate.value,
      endDate: endDate.value,
      pageNum: paging.value.pageNum,
      pageSize: paging.value.pageSize
    })

    getContentList = response.boardList
    totalCount.value = response.totalCount

    paging.value.endPage = Math.ceil(totalCount.value / paging.value.pageSize)

  } catch (error) {
    console.error('전송 에러:', error)
  }
}

onMounted(async () => {
  await search()
})

watch(() => route.query.pageNum, async () => {
  await search()
})


/*
todo : db에서 categoryName을 가져와서 사용 getCategoryList 추가
 */
const categoryNames = ['카테고리', 'JAVA', 'JS', 'SpringBoot', 'Android']

// 등록일, 수정일 날짜를 yyyy-MM-dd로 변경
const formatDate = (dataString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' }
  return new Date(dataString).toLocaleDateString(undefined, options)
}

const router = useRouter()
// content 함수 정의
const showContent = (boardNum) => {
  router.push({ name: 'content', query : { boardNum: boardNum } })
}

const changePage = (pageNum) => {
  router.push({ name: 'index', query: {  pageNum: pageNum } })
}

const pageWrite = () => {
  router.push({ path: 'write' })
}

</script>

<template>
  <div style="width: 100%; max-width: 800px; margin: auto">
    <h1>자유게시판 - 목록</h1>
    <br />
    <form @submit.prevent="search" accept-charset="UTF-8">
      <table style="text-align: left; border: 1px solid #dddddd; width: 100%">
        <tr>
          <td>
            등록일&nbsp; <input type="date" v-model="startDate" /> ~&nbsp;
            <input type="date" v-model="endDate" />
            &nbsp;&nbsp;
            <select id="categoryNum" v-model="categoryNum">
              <option id="0" value="0">카테고리</option>
              <option id="1" value="1">JAVA</option>
              <option id="2" value="2">JS</option>
              <option id="3" value="3">SpringBoot</option>
              <option id="4" value="4">Android</option>
            </select>
            &nbsp;&nbsp;
            <input type="search" v-model="searchId" id="searchId" style="width: 30%" placeholder="검색어를 입력하세요(제목 + 작성자)" />
            &nbsp;&nbsp;
            <input type="submit" name="search" id="search" value="검색" /><br />
          </td>
        </tr>
      </table>
      <p style="text-align: left">총 {{ totalCount }} 건</p>
    </form>

    <table style="text-align: center; width: 100%">
      <thead>
        <tr>
          <th>카테고리</th>
          <th></th>
          <th>제목</th>
          <th>작성자</th>
          <th>조회수</th>
          <th>등록 일자</th>
          <th>수정 일자</th>
         <hr>
        </tr>
      </thead>
      <hr>


      <!--      boardList-->
      <tbody>
        <tr v-for="(boardList, boardNum) in getContentList" :key="boardNum">
          <td>{{ categoryNames[boardList.categoryNum] }}</td>
          <td></td>
          <td>
            <a
              @click.prevent="showContent(boardList.boardNum)"
              style=" text-decoration: none; color: black"
            >
              {{ boardList.title }}
            </a>
          </td>
          <td>{{ boardList.writer }}</td>
          <td>{{ boardList.hit }}</td>
          <td>{{ formatDate(boardList.writeDate) }}</td>
          <td v-if="!boardList.modifyDate || boardList.modifyDate === boardList.writeDate">-</td>
          <td v-else>{{ formatDate(boardList.modifyDate) }}</td>
        </tr>
      </tbody>

    </table>
    <p />

    <!--    paging-->
    <div style="display: flex; justify-content: center">
            <button v-if="paging.pageNum > 1" @click="changePage(paging.pageNum -1)">이전</button>
            <button v-for="i in (parseInt(paging.endPage))" :key="'page-' + i" @click="changePage(i)">{{ i }}</button>
            <button v-if="paging.pageNum < paging.endPage" @click="changePage(paging.pageNum +1)">다음</button>
    </div>

    <table style="text-align: right; width: 80%">
      <tr>
        <td colspan="5">
          <div class="form-group">
            <button @click.prevent="pageWrite">등록</button>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>