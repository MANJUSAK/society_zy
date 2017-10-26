package com.goodsoft.society_zy.service.lmpl;

import com.goodsoft.society_zy.domain.dao.FileDao;
import com.goodsoft.society_zy.domain.entity.file.FileData;
import com.goodsoft.society_zy.service.FileService;
import com.goodsoft.society_zy.util.FileUploadUtil;
import com.goodsoft.society_zy.util.GetOsNameUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * function 文件上传业务接口实现类
 * Created by 严彬荣 on 2017/8/4.
 * version v1.0
 */
@SuppressWarnings("ALL")
@Service
public class FileServicelmpl implements FileService {

    @Resource
    private SqlSessionTemplate sqlSession;
    @Resource
    private FileUploadUtil fileUploadUtil;
    @Resource
    private FileDao dao;
    //实例化获取操作系统类型工具类
    private GetOsNameUtil getOsNameUtil = GetOsNameUtil.getInstance();
    //实例化日志管理
    private final Logger logger = LoggerFactory.getLogger(FileServicelmpl.class);

    /**
     * 文件上传业务处理方法
     *
     * @param files    上传的文件,
     * @param fileType 上传文件类型（苗木、设备租赁等），
     * @param fileId   文件编号（用于查询文件）。
     * @return int 文件上传处理状态（0为成功，其余都失败）
     * @throws Exception
     */
    @Override
    @Transactional
    public int fileUploadService(MultipartFile[] files, String fileType, String fileId) {
        //判断文件是图片还是文档 start
        switch (fileType) {
            case "document":
                //判断文件是否为空
                if (!(files[0].isEmpty())) {
                    //判断文件大小是否小于30M start
                    if (files[0].getSize() > 30000000) {
                        return 601;
                    }
                    //判断文件大小是否小于30M end
                    // 获取文件名
                    String fileName = files[0].getOriginalFilename().toLowerCase();
                    // 判断文件格式是否正确 start
                    if (!(fileName.endsWith("doc") || fileName.endsWith("docx") || fileName.endsWith("pdf"))) {
                        return 603;
                    }
                    // 判断文件格式是否正确 end
                } else {
                    return 604;
                }
                // 判断文件格是否为空 end
                break;
            //判断文件是图片还是文档 end
            //判断文件是否为Excel start
            default:
                //判断文件是否为空
                if (!(files[0].isEmpty())) {
                    //判断文件大小是否小于10M start
                    if (files[0].getSize() > 10000000) {
                        return 601;
                    }
                    //判断文件大小是否小于30M end
                    // 获取文件名
                    String fileName = files[0].getOriginalFilename().toLowerCase();
                    // 判断文件格式是否正确 start
                    if (!(fileName.endsWith("xlsx") || fileName.endsWith("xls"))) {
                        return 603;
                    }
                    // 判断文件格式是否正确 end
                } else {
                    return 604;
                }
                break;
            //判断文件是否为Excel end
        }
        //文件保存根目录
        String var1 = null;
        if (this.getOsNameUtil.getOsName()) {
            //Linux文件路径
            var1 = "/usr/society_zy";
        } else {
            //windows文件路径
            var1 = "D:/society_zy";
        }
        //文件保存 start
        List<String> fileList = null;
        SqlSession session = null;
        try {
            //保存文件到服务器并获取文件相对路径
            fileList = this.fileUploadUtil.fileUpload(files, fileType, var1);
            String sort = null;
            //获取文件类型 start
            switch (fileType) {
                case "document":
                    sort = "文档文件";
                    break;
                case "excel":
                    sort = "表格文档";
                    break;
                default:
                    sort = "未知分类";
                    break;
            }
            //获取文件类型 end
            //文件信息保存 start
            session = this.sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            FileDao fileDao = session.getMapper(FileDao.class);
            for (int i = 0, length = fileList.size(); i < length; ++i) {
                FileData file = new FileData();
                //设置文件编号
                file.setFileId(fileId);
                //设置根目录
                file.setBases(var1);
                //设置文件类别
                file.setSort(sort);
                //截取新文件名字符位置
                int j = fileList.get(i).lastIndexOf("/") + 1;
                //截取文件后缀字符位置
                int s = files[i].getOriginalFilename().lastIndexOf(".");
                //获取文件新命名
                file.setNewFileName(fileList.get(i).substring(j, fileList.get(i).length()));
                //获取原文件名
                file.setFileName(files[i].getOriginalFilename());
                //获取文件后缀
                file.setSuffix(files[i].getOriginalFilename().substring(s, files[i].getOriginalFilename().length()));
                //设置文件路径
                file.setPath(fileList.get(i));
                fileDao.saveFileDao(file);
            }
            session.commit();
            session.clearCache();
            //文件信息保存 end
            //清除集合里的内容  避免数据混乱
            fileList.clear();
        } catch (Exception e) {
            fileList.clear();
            session.rollback();
            System.out.println(e.toString());
            this.logger.error(e.toString());
            return 600;
        } finally {
            session.close();
        }
        return 0;
        //文件保存 end
    }
}
